package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aston.data.database.datasource.CharacterDataSource
import com.aston.data.database.datasource.EpisodeDataSource
import com.aston.data.database.datasource.LocationDataSource
import com.aston.data.paging.CharactersPagingSource
import com.aston.data.remote.CharactersService
import com.aston.data.util.mapper.MapperCharacterData
import com.aston.data.util.mapper.MapperEpisodeData
import com.aston.data.util.mapper.MapperLocationData
import com.aston.data.util.resource.networkBoundResource
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.model.location.LocationInfo
import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PAGE_SIZE = 1

class CharactersRepositoryImpl @Inject constructor(
    private val service: CharactersService,
    private val characterDataSource: CharacterDataSource,
    private val locationDataSource: LocationDataSource,
    private val episodeDataSource: EpisodeDataSource,
    private val mapperEpisode: MapperEpisodeData,
    private val mapperCharacter: MapperCharacterData,
    private val mapperLocation: MapperLocationData,
) : CharactersRepository {

    override fun fetchCharactersThoughService(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfo>> {
        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            CharactersPagingSource(
                characterName,
                characterStatus,
                characterSpecies,
                characterGender,
                characterDataSource,
                service
            )
        }).flow.map { paging ->
            paging.map { character ->
                mapperCharacter.mapToCharacter(character)
            }
        }
    }

    override fun fetchCharactersThoughDatabase(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfo>> {
        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            characterDataSource.fetchCharacters(
                characterName, characterStatus, characterSpecies, characterGender
            )
        }).flow.map { paging ->
            paging.map { character ->
                mapperCharacter.mapToCharacter(character)
            }
        }
    }

    override fun fetchEpisodesByIds(episodeIds: List<Int>): Flow<List<EpisodeInfo>> {
        return networkBoundResource(query = {
            episodeDataSource.fetchEpisodesByIds(episodeIds).map { episodes ->
                episodes.map { episode ->
                    mapperEpisode.mapToEpisode(episode)
                }
            }
        }, fetch = {
            service.getEpisodesForCharacterByUrl(episodeIds)
        }, saveFetchResult = { episodes ->
            episodeDataSource.insertEpisodes(episodes)
        })
    }

    override fun fetchLocationById(locationId: Int): Flow<LocationInfo> {
        return networkBoundResource(query = {
            locationDataSource.fetchLocationById(locationId).map { location ->
                mapperLocation.mapToLocationInfo(location)
            }
        }, fetch = {
            service.fetchLocationById(locationId)
        }, saveFetchResult = { location ->
            locationDataSource.insertLocation(location.results[0])
        })
    }

    override fun fetchOriginLocationByName(originLocationName: String): Flow<LocationInfo> {
        return networkBoundResource(query = {
            locationDataSource.fetchOriginLocationByName(originLocationName).map { location ->
                mapperLocation.mapToLocationInfo(location)
            }
        }, fetch = {
            service.fetchOriginLocationByName(originLocationName)
        }, saveFetchResult = { location ->
            locationDataSource.insertOriginLocation(location.results[0])
        })
    }
}