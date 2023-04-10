package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aston.data.database.datasource.CharacterDataSource
import com.aston.data.database.datasource.EpisodeDataSource
import com.aston.data.database.datasource.LocationDataSource
import com.aston.data.paging.CharactersPagingSource
import com.aston.data.remote.CharactersService
import com.aston.data.util.mapper.CharacterDataMapper
import com.aston.data.util.mapper.EpisodeDataMapper
import com.aston.data.util.mapper.LocationDataMapper
import com.aston.data.util.resource.networkBoundResource
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.model.location.LocationInfo
import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 1
private const val RESULT_POSITION = 0

class CharactersRepositoryImpl @Inject constructor(
    private val service: CharactersService,
    private val characterDataSource: CharacterDataSource,
    private val locationDataSource: LocationDataSource,
    private val episodeDataSource: EpisodeDataSource,
    private val mapperEpisode: EpisodeDataMapper,
    private val mapperCharacter: CharacterDataMapper,
    private val mapperLocation: LocationDataMapper,
) : CharactersRepository {

    override fun fetchCharactersThoughService(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfo>> {
        return mapperCharacter.mapToCharacterFlowPaging(
            Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = {
                    CharactersPagingSource(
                        characterName,
                        characterStatus,
                        characterSpecies,
                        characterGender,
                        characterDataSource,
                        service,
                        mapperCharacter
                    )
                }).flow
        )
    }

    override fun fetchCharactersThoughDatabase(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfo>> {
        return mapperCharacter.mapToCharacterFlowPaging(
            Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = {
                    characterDataSource.fetchCharacters(
                        characterName, characterStatus, characterSpecies, characterGender
                    )
                }).flow
        )
    }

    override fun fetchEpisodesByIds(episodeIds: List<Int>): Flow<List<EpisodeInfo>> {
        return networkBoundResource(query = {
            mapperEpisode.mapToEpisodeFlowList(episodeDataSource.fetchEpisodesByIds(episodeIds))
        }, fetch = {
            service.fetchEpisodesByIds(episodeIds)
        }, saveFetchResult = { episodes ->
            episodeDataSource.insertEpisodes(mapperEpisode.mapToEpisodesListData(episodes))
        })
    }

    override suspend fun fetchLocationByIdService(locationId: Int): LocationInfo {
        val location = mapperLocation.mapToLocationInfoData(service.fetchLocationById(locationId))
        locationDataSource.insertLocation(location)
        return mapperLocation.mapToLocationInfo(location)
    }

    override fun fetchLocationByIdDatabase(locationId: Int): Flow<LocationInfo> {
        return mapperLocation.mapToLocationFlow(locationDataSource.fetchLocationById(locationId))
    }

    override suspend fun fetchOriginLocationByNameService(originLocationName: String): LocationInfo {
        val originLocation = mapperLocation.mapToLocationInfoData(
            service.fetchOriginLocationByName(originLocationName).locations[RESULT_POSITION]
        )
        locationDataSource.insertOriginLocation(originLocation)
        return mapperLocation.mapToLocationInfo(originLocation)
    }

    override fun fetchOriginLocationByNameDatabase(originLocationName: String): Flow<LocationInfo> {
        return mapperLocation.mapToLocationFlow(
            locationDataSource.fetchOriginLocationByName(
                originLocationName
            )
        )
    }
}