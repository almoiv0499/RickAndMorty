package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aston.data.database.datasource.CharacterDataSource
import com.aston.data.database.datasource.LocationDataSource
import com.aston.data.paging.LocationsPagingSource
import com.aston.data.remote.LocationsService
import com.aston.data.util.mapper.CharacterDataMapper
import com.aston.data.util.mapper.LocationDataMapper
import com.aston.data.util.resource.networkBoundResource
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.location.LocationInfo
import com.aston.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 1

class LocationsRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val characterDataSource: CharacterDataSource,
    private val service: LocationsService,
    private val mapperLocation: LocationDataMapper,
    private val mapperCharacter: CharacterDataMapper,
) : LocationRepository {

    override fun fetchLocationsThoughService(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ): Flow<PagingData<LocationInfo>> {
        return mapperLocation.mapToLocationFlowPaging(
            Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
                LocationsPagingSource(
                    locationName,
                    locationType,
                    locationDimension,
                    locationDataSource,
                    service,
                    mapperLocation
                )
            }).flow
        )
    }

    override fun fetchLocationsThoughDatabase(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ): Flow<PagingData<LocationInfo>> {
        return mapperLocation.mapToLocationFlowPaging(
            Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
                locationDataSource.fetchLocations(locationName, locationType, locationDimension)
            }).flow
        )
    }

    override fun fetchCharactersByIds(characterIds: List<Int>): Flow<List<CharacterInfo>> {
        return networkBoundResource(query = {
            mapperCharacter.mapToCharacterFlowList(
                characterDataSource.fetchCharactersById(characterIds)
            )
        }, fetch = {
            service.fetchCharactersByIds(characterIds)
        }, saveFetchResult = { characters ->
            characterDataSource.insertCharacters(mapperCharacter.mapToCharacterListData(characters))
        })
    }
}