package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aston.data.database.ApplicationDatabase
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
    private val database: ApplicationDatabase,
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
                characterName, characterStatus, characterSpecies, characterGender, database, service
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
            database.charactersDao()
                .fetchCharacters(characterName, characterStatus, characterSpecies, characterGender)
        }).flow.map { paging ->
            paging.map { character ->
                mapperCharacter.mapToCharacter(character)
            }
        }
    }

    override fun fetchEpisodesByIds(episodeIds: List<Int>): Flow<List<EpisodeInfo>> {
        return networkBoundResource(query = {
            database.episodeDao().fetchEpisodesByIds(episodeIds).map { episodes ->
                episodes.map { episode ->
                    mapperEpisode.mapToEpisode(episode)
                }
            }
        }, fetch = {
            service.getEpisodesForCharacterByUrl(episodeIds)
        }, saveFetchResult = { episodes ->
            database.episodeDao().insertEpisodes(episodes)
        })
    }

    override fun fetchLocationById(locationId: Int): Flow<LocationInfo> {
        return networkBoundResource(query = {
            database.locationDao().fetchLocationById(locationId).map { location ->
                mapperLocation.mapToLocationInfo(location)
            }
        }, fetch = {
            service.fetchLocationById(locationId)
        }, saveFetchResult = { location ->
            database.locationDao().insertLocation(location.results[0])
        })
    }

    override fun fetchOriginLocationByName(originLocationName: String): Flow<LocationInfo> {
        return networkBoundResource(query = {
            database.locationDao().fetchOriginLocationByName(originLocationName).map { location ->
                mapperLocation.mapToLocationInfo(location)
            }
        }, fetch = {
            service.fetchOriginLocationByName(originLocationName)
        }, saveFetchResult = { location ->
            database.locationDao().insertOriginLocation(location.results[0])
        })
    }
}