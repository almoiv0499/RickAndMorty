package com.aston.domain.usecase

import androidx.paging.PagingData
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.repository.CharactersRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCharactersThoughDatabaseUseCase @Inject constructor(
    private val repository: CharactersRemoteRepository,
) {

    operator fun invoke(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfo>> = repository.fetchCharactersThoughDatabase(
        characterName, characterStatus, characterSpecies, characterGender
    )

}