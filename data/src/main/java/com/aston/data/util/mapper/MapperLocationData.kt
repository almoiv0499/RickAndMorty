package com.aston.data.util.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.data.model.location.LocationInfoData
import com.aston.data.model.location_dto.LocationInfoDataDto
import com.aston.domain.model.location.LocationInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MapperLocationData @Inject constructor() {

    fun mapToFlowData(flow: Flow<PagingData<LocationInfoData>>): Flow<PagingData<LocationInfo>> {
        return flow.map { paging ->
            mapToPagingLocationInfo(paging)
        }
    }

    private fun mapToPagingLocationInfo(pagingData: PagingData<LocationInfoData>): PagingData<LocationInfo> {
        return pagingData.map { location ->
            mapToLocationInfo(location)
        }
    }

    fun mapToLocationInfo(locationData: LocationInfoData): LocationInfo {
        return LocationInfo(
            created = locationData.created,
            dimension = locationData.dimension,
            id = locationData.id,
            name = locationData.name,
            residents = locationData.residents,
            type = locationData.type,
            url = locationData.url
        )
    }

    fun mapFromLocationInfoDto(locationData: LocationInfoDataDto): LocationInfoData {
        return LocationInfoData(
            created = locationData.locationCreated,
            dimension = locationData.locationDimension,
            id = locationData.locationId,
            name = locationData.locationName,
            residents = locationData.characters,
            type = locationData.locationType,
            url = locationData.url
        )
    }

}