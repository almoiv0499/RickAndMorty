package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.flowable
import androidx.paging.rxjava3.observable
import com.aston.data.database.ApplicationDatabase
import com.aston.data.paging.EpisodePagingSource
import com.aston.data.remote.EpisodeService
import com.aston.data.util.mapper.MapperEpisodeData
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.repository.EpisodeRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

private const val PAGE_SIZE = 1

class EpisodeRepositoryImpl @Inject constructor(
    private val database: ApplicationDatabase,
    private val service: EpisodeService,
    private val mapperEpisode: MapperEpisodeData,
) : EpisodeRepository {

    override fun fetchEpisodesThoughService(episodeName: String): Flowable<PagingData<EpisodeInfo>> {
        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            EpisodePagingSource(episodeName, database, service)
        }).flowable.map { paging ->
            paging.map { episode ->
                mapperEpisode.mapToEpisode(episode)
            }
        }
    }

    override fun fetchEpisodesThoughDatabase(
        episodeName: String,
    ): Flowable<PagingData<EpisodeInfo>> {
        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            database.episodeDao().fetchEpisodes(episodeName)
        }).flowable.map { paging ->
            paging.map { episode ->
                mapperEpisode.mapToEpisode(episode)
            }
        }
    }
}