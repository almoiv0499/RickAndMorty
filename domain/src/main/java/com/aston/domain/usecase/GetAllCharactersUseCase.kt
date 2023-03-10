package com.aston.domain.usecase

import com.aston.domain.repository.CharactersRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetAllCharactersUseCase(
    private val repository: CharactersRemoteRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() = withContext(ioDispatcher) {
        repository.getAllCharacters()
    }

}