package com.aston.rickandmorty.presentation.fragment.locations

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aston.domain.model.location.LocationInfo
import com.aston.domain.usecase.location.FetchLocationsThoughDatabaseUseCase
import com.aston.domain.usecase.location.FetchLocationsThoughServiceUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.location_details.LocationDetailsFragment
import com.aston.rickandmorty.presentation.fragment.location_filter.LocationsFilterFragment
import com.aston.rickandmorty.presentation.mapper.LocationViewMapper
import com.aston.rickandmorty.presentation.model.location.LocationInfoViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FRAGMENT_FILTER_TAG = "LocationFragmentFilter"

class LocationsViewModel @Inject constructor(
    context: Context,
    private val fetchLocationsThoughDatabaseUseCase: FetchLocationsThoughDatabaseUseCase,
    private val fetchLocationsThoughServiceUseCase: FetchLocationsThoughServiceUseCase,
    private val mapper: LocationViewMapper,
) : BaseViewModel(context) {

    private val _locationsLiveData = MutableLiveData<PagingData<LocationInfoViewModel>>()
    val locationsLiveData: LiveData<PagingData<LocationInfoViewModel>> = _locationsLiveData

    fun getLocations(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ) {
        if (hasInternetConnection()) {
            fetchLocations(
                fetchLocationsThoughServiceUseCase(
                    locationName, locationType, locationDimension
                )
            )
        } else {
            fetchLocations(
                fetchLocationsThoughDatabaseUseCase(
                    locationName, locationType, locationDimension
                )
            )
        }
    }

    private fun fetchLocations(
        useCase: Flow<PagingData<LocationInfo>>,
    ) {
        viewModelScope.launch {
            useCase
                .cachedIn(viewModelScope)
                .catch {
                    showExceptionMessage(R.string.fetchData_exceptionMessage)
                }
                .collectLatest { paging ->
                    _locationsLiveData.value = mapper.mapToLocationPagingView(paging)
                }
        }
    }

    fun launchFilterFragment() {
        launchDialogFragment(LocationsFilterFragment.newInstance(), FRAGMENT_FILTER_TAG)
    }

    fun launchLocationDetailsFragment(location: LocationInfoViewModel) {
        launchFragment(LocationDetailsFragment.newInstance(location))
    }

    fun refreshFragment() {
        refreshFragment(LocationsFragment.newInstance())
    }

}