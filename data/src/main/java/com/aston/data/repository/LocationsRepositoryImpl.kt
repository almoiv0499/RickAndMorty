package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aston.data.database.ApplicationDatabase
import com.aston.data.paging.LocationsPagingSource
import com.aston.data.remote.LocationsService
import com.aston.data.util.mapper.MapperLocationData
import com.aston.domain.model.location.LocationInfo
import com.aston.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 1

class LocationsRepositoryImpl @Inject constructor(
    private val database: ApplicationDatabase,
    private val service: LocationsService,
    private val mapper: MapperLocationData,
) : LocationRepository {

    override fun fetchLocationsThoughService(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ): Flow<PagingData<LocationInfo>> {
        return mapper.mapToFlowData(
            Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
                LocationsPagingSource(
                    locationName, locationType, locationDimension, database, service
                )
            }).flow
        )
    }

    override fun fetchLocationsThoughDatabase(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ): Flow<PagingData<LocationInfo>> {
        return mapper.mapToFlowData(
            Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
                database.locationDao().fetchLocations(locationName, locationType, locationDimension)
            }).flow
        )
    }
}