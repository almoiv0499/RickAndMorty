package com.aston.rickandmorty.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.domain.model.location.LocationInfo
import com.aston.rickandmorty.presentation.model.location.LocationInfoView
import javax.inject.Inject

class MapperLocationView @Inject constructor() {

    fun mapToLocationPagingView(paging: PagingData<LocationInfo>): PagingData<LocationInfoView> {
        return paging.map { location ->
            mapToLocationInfoView(location)
        }
    }

    private fun mapToLocationInfoView(locationInfo: LocationInfo): LocationInfoView {
        return LocationInfoView(
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