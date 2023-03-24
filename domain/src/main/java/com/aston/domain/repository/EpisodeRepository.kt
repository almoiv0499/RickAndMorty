package com.aston.domain.repository

import androidx.paging.PagingData
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.episode.EpisodeInfo
import io.reactivex.rxjava3.core.Observable

interface EpisodeRepository {

    fun fetchEpisodesThoughService(
        episodeName: String,
        episodeNumber: String,
    ): Observable<PagingData<EpisodeInfo>>

    fun fetchEpisodesThoughDatabase(
        episodeName: String,
        episodeNumber: String,
    ): Observable<PagingData<EpisodeInfo>>

    fun fetchCharactersByIdService(characterIds: List<Int>): Observable<List<CharacterInfo>>

    fun fetchCharactersByIdDatabase(characterIds: List<Int>): Observable<List<CharacterInfo>>

}