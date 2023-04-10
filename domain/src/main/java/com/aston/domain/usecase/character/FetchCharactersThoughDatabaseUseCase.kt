package com.aston.domain.usecase.character

import androidx.paging.PagingData
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCharactersThoughDatabaseUseCase @Inject constructor(
    private val repository: CharactersRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfo>> = repository.fetchCharactersThoughDatabase(
        characterName, characterStatus, characterSpecies, characterGender
    ).flowOn(ioDispatcher)

}