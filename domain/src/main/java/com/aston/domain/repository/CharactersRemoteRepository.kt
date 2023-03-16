package com.aston.domain.repository

import androidx.paging.PagingData
import com.aston.domain.model.character.CharacterDetails
import com.aston.domain.model.episode.Episode
import kotlinx.coroutines.flow.Flow

interface CharactersRemoteRepository {

    fun fetchCharactersThoughService(): Flow<PagingData<CharacterDetails>>

    fun fetchCharactersThoughDatabase(): Flow<PagingData<CharacterDetails>>

    fun fetchEpisodesByIdsUseCase(episodeIds: List<Int>): Flow<List<Episode>>

}