package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aston.data.database.ApplicationDatabase
import com.aston.data.paging.LocationsPagingSource
import com.aston.data.remote.LocationsService
import com.aston.data.util.mapper.MapperCharacterData
import com.aston.data.util.mapper.MapperLocationData
import com.aston.data.util.resource.networkBoundResource
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.location.LocationInfo
import com.aston.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 1

class LocationsRepositoryImpl @Inject constructor(
    private val database: ApplicationDatabase,
    private val service: LocationsService,
    private val mapperLocation: MapperLocationData,
    private val mapperCharacter: MapperCharacterData,
) : LocationRepository {

    override fun fetchLocationsThoughService(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ): Flow<PagingData<LocationInfo>> {
        return mapperLocation.mapToFlowData(
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
        return mapperLocation.mapToFlowData(
            Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
                database.locationDao().fetchLocations(locationName, locationType, locationDimension)
            }).flow
        )
    }

    override fun fetchCharactersById(characterIds: List<Int>): Flow<List<CharacterInfo>> {
        return networkBoundResource(query = {
            mapperCharacter.mapToFLowListData(
                database.charactersDao().fetchCharactersById(characterIds)
            )
        }, fetch = {
            service.fetchCharactersById(characterIds)
        }, saveFetchResult = { characters ->
            database.charactersDao().insertCharacters(characters)
        })
    }
}