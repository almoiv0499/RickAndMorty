package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.observable
import com.aston.data.database.datasource.CharacterDataSource
import com.aston.data.database.datasource.EpisodeDataSource
import com.aston.data.paging.EpisodePagingSource
import com.aston.data.remote.EpisodeService
import com.aston.data.util.mapper.MapperCharacterData
import com.aston.data.util.mapper.MapperEpisodeData
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.repository.EpisodeRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

private const val PAGE_SIZE = 1

class EpisodeRepositoryImpl @Inject constructor(
    private val episodeDataSource: EpisodeDataSource,
    private val characterDataSource: CharacterDataSource,
    private val service: EpisodeService,
    private val mapperEpisode: MapperEpisodeData,
    private val mapperCharacter: MapperCharacterData,
) : EpisodeRepository {

    override fun fetchEpisodesThoughService(
        episodeName: String,
        episodeNumber: String,
    ): Observable<PagingData<EpisodeInfo>> {
        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            EpisodePagingSource(
                episodeName, episodeNumber, episodeDataSource, service, mapperEpisode
            )
        }).observable.map { paging ->
            paging.map { episode ->
                mapperEpisode.mapToEpisode(episode)
            }
        }
    }

    override fun fetchEpisodesThoughDatabase(
        episodeName: String,
        episodeNumber: String,
    ): Observable<PagingData<EpisodeInfo>> {
        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            episodeDataSource.fetchEpisodes(episodeName, episodeNumber)
        }).observable.map { paging ->
            paging.map { episode ->
                mapperEpisode.mapToEpisode(episode)
            }
        }
    }

    override fun fetchCharactersByIdService(characterIds: List<Int>): Observable<List<CharacterInfo>> {
        return service.fetchCharactersById(characterIds).map { characters ->
            characterDataSource.insertCharactersForEpisode(characters.map { character ->
                mapperCharacter.mapFromCharacterDto(character)
            })
            characterDataSource.fetchCharactersByIdForEpisode(characterIds)
        }.flatMap {
            it.map { characters ->
                characters.map { character ->
                    mapperCharacter.mapToCharacter(character)
                }
            }
        }
    }

    override fun fetchCharactersByIdDatabase(characterIds: List<Int>): Observable<List<CharacterInfo>> {
        return characterDataSource.fetchCharactersByIdForEpisode(characterIds).map { characters ->
            characters.map { character ->
                mapperCharacter.mapToCharacter(character)
            }
        }
    }
}