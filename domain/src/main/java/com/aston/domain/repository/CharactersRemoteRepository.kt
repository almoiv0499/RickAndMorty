package com.aston.domain.repository

import androidx.paging.PagingData
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.episode.Episode
import kotlinx.coroutines.flow.Flow

interface CharactersRemoteRepository {

    fun fetchCharactersThoughService(): Flow<PagingData<CharacterInfo>>

    fun fetchCharactersThoughDatabase(): Flow<PagingData<CharacterInfo>>

    fun fetchEpisodesByIdsUseCase(episodeIds: List<Int>): Flow<List<Episode>>

}