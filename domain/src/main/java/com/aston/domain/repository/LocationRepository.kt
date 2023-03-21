package com.aston.domain.repository

import androidx.paging.PagingData
import com.aston.domain.model.location.LocationInfo
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun fetchLocationsThoughService(
        locationName: String, locationType: String, locationDimension: String,
    ): Flow<PagingData<LocationInfo>>

    fun fetchLocationsThoughDatabase(
        locationName: String, locationType: String, locationDimension: String,
    ): Flow<PagingData<LocationInfo>>

}