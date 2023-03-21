package com.aston.rickandmorty.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.domain.model.location.LocationInfo
import com.aston.rickandmorty.presentation.model.location.LocationInfoView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MapperLocationView @Inject constructor() {

    fun mapToFlowView(flow: Flow<PagingData<LocationInfo>>): Flow<PagingData<LocationInfoView>> {
        return flow.map { paging ->
            mapToPagingDataView(paging)
        }
    }

    private fun mapToPagingDataView(paging: PagingData<LocationInfo>): PagingData<LocationInfoView> {
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