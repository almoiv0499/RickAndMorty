package com.aston.rickandmorty.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.domain.model.location.LocationInfo
import com.aston.rickandmorty.presentation.model.location.LocationInfoViewModel
import javax.inject.Inject

class LocationViewMapper @Inject constructor() {

    fun mapToLocationPagingView(paging: PagingData<LocationInfo>): PagingData<LocationInfoViewModel> {
        return paging.map { location ->
            mapToLocationInfoView(location)
        }
    }

    fun mapToLocationInfoView(locationInfo: LocationInfo): LocationInfoViewModel {
        return LocationInfoViewModel(
            created = locationInfo.created,
            dimension = locationInfo.dimension,
            id = locationInfo.id,
            name = locationInfo.name,
            residents = locationInfo.residents,
            type = locationInfo.type,
            url = locationInfo.url
        )
    }

}