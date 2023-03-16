package com.aston.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aston.data.database.CharacterDatabase
import com.aston.data.model.character.CharacterData
import com.aston.data.paging.CharactersPagingSource
import com.aston.data.remote.CharactersService
import com.aston.domain.model.episode.Episode
import com.aston.domain.repository.CharactersRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRemoteRepositoryImpl @Inject constructor(
    private val service: CharactersService,
    private val database: CharacterDatabase
) : CharactersRemoteRepository {

    suspend fun response(): Boolean = service.fetchCharacters().isSuccessful

    fun fetchCharactersByApiWithPaging(): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(1),
            pagingSourceFactory = {
                CharactersPagingSource(database, service)
            }
        ).flow
    }

    fun fetchCharactersByDatabaseWithPaging(): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(1),
            pagingSourceFactory = {
                database.characterDao().fetchCharacters()
            }
        ).flow
    }

    override suspend fun getEpisodesForCharacterByUrl(episodeIds: List<Int>): List<Episode> {
       return emptyList()
    }
}