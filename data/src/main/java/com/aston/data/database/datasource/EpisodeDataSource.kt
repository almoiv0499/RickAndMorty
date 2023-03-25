package com.aston.data.database.datasource

import androidx.paging.PagingSource
import com.aston.data.database.dao.EpisodeDao
import com.aston.data.model.episode.EpisodeInfoData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeDataSource @Inject constructor(
    private val dao: EpisodeDao,
) {

    fun fetchEpisodes(
        episodeName: String,
        episodeNumber: String,
    ): PagingSource<Int, EpisodeInfoData> {
        return dao.fetchEpisodes(episodeName, episodeNumber)
    }

    fun fetchEpisodesByIds(ids: List<Int>): Flow<List<EpisodeInfoData>> {
        return dao.fetchEpisodesByIds(ids)
    }

    fun insertEpisodes(episodes: List<EpisodeInfoData>) {
        dao.insertEpisodes(episodes)
    }

}