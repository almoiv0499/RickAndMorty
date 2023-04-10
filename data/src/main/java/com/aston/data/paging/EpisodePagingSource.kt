package com.aston.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.aston.data.database.datasource.EpisodeDataSource
import com.aston.data.model.episode.EpisodeInfoData
import com.aston.data.remote.EpisodeService
import com.aston.data.util.mapper.EpisodeDataMapper
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val BY_ONE = 1

class EpisodePagingSource(
    private val episodeName: String,
    private val episodeNumber: String,
    private val episodeDataSource: EpisodeDataSource,
    private val service: EpisodeService,
    private val mapperEpisode: EpisodeDataMapper,
) : RxPagingSource<Int, EpisodeInfoData>() {

    override fun getRefreshKey(state: PagingState<Int, EpisodeInfoData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(BY_ONE) ?: anchorPage?.nextKey?.minus(BY_ONE)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, EpisodeInfoData>> {
        val currentPage = params.key ?: BY_ONE

        return service.fetchEpisodesByPage(currentPage, episodeName, episodeNumber)
            .subscribeOn(Schedulers.io()).map { episodeResult ->
                val episodes = episodeResult.episodes.map { episode ->
                    mapperEpisode.mapToEpisodeData(episode)
                }
                episodeDataSource.insertEpisodes(episodes)
                LoadResult.Page(
                    data = episodes,
                    prevKey = if (currentPage == BY_ONE) null else currentPage.minus(BY_ONE),
                    nextKey = if (episodes.isEmpty()) null else currentPage.plus(BY_ONE)
                ) as LoadResult<Int, EpisodeInfoData>
            }.onErrorReturn { throwable ->
                LoadResult.Error(throwable)
            }
    }
}