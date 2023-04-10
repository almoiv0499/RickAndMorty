package com.aston.data.database.datasource

import androidx.paging.PagingSource
import com.aston.data.database.dao.LocationDao
import com.aston.data.model.location.LocationInfoData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationDataSource @Inject constructor(
    private val dao: LocationDao,
) {

    fun fetchLocations(
        locationName: String, locationType: String, locationDimension: String,
    ): PagingSource<Int, LocationInfoData> {
        return dao.fetchLocations(locationName, locationType, locationDimension)
    }

    fun fetchLocationById(locationId: Int): Flow<LocationInfoData> {
        return dao.fetchLocationById(locationId)
    }

    fun fetchOriginLocationByName(originLocationName: String): Flow<LocationInfoData> {
        return dao.fetchOriginLocationByName(originLocationName)
    }

    suspend fun insertLocations(locations: List<LocationInfoData>) {
        dao.insertLocations(locations)
    }

    suspend fun insertLocation(location: LocationInfoData) {
        dao.insertLocation(location)
    }

    suspend fun insertOriginLocation(originLocation: LocationInfoData) {
        dao.insertOriginLocation(originLocation)
    }
}