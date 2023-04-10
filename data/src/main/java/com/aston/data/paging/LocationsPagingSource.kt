package com.aston.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aston.data.database.datasource.LocationDataSource
import com.aston.data.model.location.LocationInfoData
import com.aston.data.remote.LocationsService
import com.aston.data.util.mapper.LocationDataMapper

private const val BY_ONE = 1

class LocationsPagingSource(
    private val locationName: String,
    private val locationType: String,
    private val locationDimension: String,
    private val locationDataSource: LocationDataSource,
    private val service: LocationsService,
    private val mapperLocation: LocationDataMapper,
) : PagingSource<Int, LocationInfoData>() {

    override fun getRefreshKey(state: PagingState<Int, LocationInfoData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(BY_ONE) ?: anchorPage?.nextKey?.minus(BY_ONE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationInfoData> {
        val currentPage = params.key ?: BY_ONE

        return try {

            val response = service.fetchLocationsByPage(
                currentPage, locationName, locationType, locationDimension
            )
            val data = response.locations.map { location ->
                mapperLocation.mapToLocationInfoData(location)
            }

            locationDataSource.insertLocations(data)

            val prevKey = if (currentPage == BY_ONE) null else currentPage.minus(BY_ONE)
            val nextKey = if (data.isEmpty()) null else currentPage.plus(BY_ONE)

            LoadResult.Page(
                data = data, prevKey = prevKey, nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }
}