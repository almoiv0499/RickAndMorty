package com.aston.rickandmorty.presentation.fragment.locations

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.paging.PagingData
import com.aston.domain.usecase.location.FetchLocationsThoughDatabaseUseCase
import com.aston.domain.usecase.location.FetchLocationsThoughServiceUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.location_details.LocationDetailsFragment
import com.aston.rickandmorty.presentation.mapper.MapperLocationView
import com.aston.rickandmorty.presentation.model.location.LocationInfoView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val context: Context,
    private val fetchLocationsThoughDatabaseUseCase: FetchLocationsThoughDatabaseUseCase,
    private val fetchLocationsThoughServiceUseCase: FetchLocationsThoughServiceUseCase,
    private val mapper: MapperLocationView,
) : BaseViewModel() {

    fun locationsFlow(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ): Flow<PagingData<LocationInfoView>> {
        return if (hasInternetConnection()) {
            locationService(locationName, locationType, locationDimension)
        } else {
            locationsDatabase(locationName, locationType, locationDimension)
        }
    }

    private fun locationService(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ): Flow<PagingData<LocationInfoView>> {
        return mapper.mapToFlowView(
            fetchLocationsThoughServiceUseCase(
                locationName, locationType, locationDimension
            )
        )
    }

    private fun locationsDatabase(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ): Flow<PagingData<LocationInfoView>> {
        return mapper.mapToFlowView(
            fetchLocationsThoughDatabaseUseCase(
                locationName, locationType, locationDimension
            )
        )
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

    fun launchLocationDetailsFragment(location: LocationInfoView) {
        launchFragment(LocationDetailsFragment.newInstance(location))
    }

}