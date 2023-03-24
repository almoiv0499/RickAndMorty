package com.aston.rickandmorty.presentation.fragment.locations

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aston.domain.model.location.LocationInfo
import com.aston.domain.usecase.location.FetchLocationsThoughDatabaseUseCase
import com.aston.domain.usecase.location.FetchLocationsThoughServiceUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.location_details.LocationDetailsFragment
import com.aston.rickandmorty.presentation.fragment.location_filter.LocationsFilterFragment
import com.aston.rickandmorty.presentation.mapper.MapperLocationView
import com.aston.rickandmorty.presentation.model.location.LocationInfoView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val FRAGMENT_FILTER_TAG = "LocationFragmentFilter"

class LocationsViewModel @Inject constructor(
    private val context: Context,
    private val fetchLocationsThoughDatabaseUseCase: FetchLocationsThoughDatabaseUseCase,
    private val fetchLocationsThoughServiceUseCase: FetchLocationsThoughServiceUseCase,
    private val mapper: MapperLocationView,
) : BaseViewModel() {

    private val _locationsLiveData = MutableLiveData<PagingData<LocationInfoView>>()
    val locationsLiveData: LiveData<PagingData<LocationInfoView>> = _locationsLiveData

    fun locationsFlow(
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
        useCase.cachedIn(viewModelScope).onEach { paging ->
            _locationsLiveData.value = mapper.mapToLocationPagingView(paging)
        }.launchIn(viewModelScope)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capability = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capability.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun launchFilterFragment() {
        launchDialogFragment(LocationsFilterFragment.newInstance(), FRAGMENT_FILTER_TAG)
    }

    fun launchLocationDetailsFragment(location: LocationInfoView) {
        launchFragment(LocationDetailsFragment.newInstance(location))
    }

    fun refreshFragment() {
        refreshFragment(LocationsFragment.newInstance())
    }

}