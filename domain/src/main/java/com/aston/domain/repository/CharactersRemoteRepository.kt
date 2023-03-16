package com.aston.domain.repository

import com.aston.domain.model.episode.Episode

interface CharactersRemoteRepository {

    suspend fun getEpisodesForCharacterByUrl(episodeIds: List<Int>): List<Episode>

    // Доработать
//    fun fetchAndCacheCharactersByPage(page: Int): Flow<PagingData<CharacterDetails>>

}