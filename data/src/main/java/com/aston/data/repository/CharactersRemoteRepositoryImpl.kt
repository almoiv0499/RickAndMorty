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
import com.aston.data.util.resource.networkBoundResource
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.episode.Episode
import com.aston.domain.repository.CharactersRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PAGE_SIZE = 1

class CharactersRemoteRepositoryImpl @Inject constructor(
    private val service: CharactersService,
    private val database: ApplicationDatabase,
    private val mapperEpisode: MapperEpisodeData,
    private val mapperCharacter: MapperCharacterData,
) : CharactersRemoteRepository {

    override fun fetchCharactersThoughService(): Flow<PagingData<CharacterInfo>> {
        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            CharactersPagingSource(database, service)
        }).flow.map { paging ->
            paging.map { character ->
                mapperCharacter.mapToCharacter(character)
            }
        }
    }

    override fun fetchCharactersThoughDatabase(): Flow<PagingData<CharacterInfo>> {
        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            database.charactersDao().fetchCharacters()
        }).flow.map { paging ->
            paging.map { character ->
                mapperCharacter.mapToCharacter(character)
            }
        }
    }

    override fun fetchEpisodesByIdsUseCase(episodeIds: List<Int>): Flow<List<Episode>> {
        return networkBoundResource(query = {
            database.episodeDao().fetchEpisodes(episodeIds).map { episodes ->
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
}