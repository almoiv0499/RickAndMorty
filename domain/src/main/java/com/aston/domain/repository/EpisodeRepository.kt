package com.aston.domain.repository

import androidx.paging.PagingData
import com.aston.domain.model.episode.EpisodeInfo
import io.reactivex.rxjava3.core.Flowable

interface EpisodeRepository {

    fun fetchEpisodesThoughService(
        episodeName: String
    ): Flowable<PagingData<EpisodeInfo>>

    fun fetchEpisodesThoughDatabase(
        episodeName: String
    ): Flowable<PagingData<EpisodeInfo>>

}