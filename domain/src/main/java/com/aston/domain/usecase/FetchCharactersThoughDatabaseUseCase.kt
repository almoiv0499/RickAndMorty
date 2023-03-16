package com.aston.domain.usecase

import androidx.paging.PagingData
import com.aston.domain.model.character.CharacterDetails
import com.aston.domain.repository.CharactersRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCharactersThoughDatabaseUseCase @Inject constructor(
    private val repository: CharactersRemoteRepository,
) {

    operator fun invoke(): Flow<PagingData<CharacterDetails>> =
        repository.fetchCharactersThoughDatabase()

}