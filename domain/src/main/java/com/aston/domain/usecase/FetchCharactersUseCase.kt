package com.aston.domain.usecase

import com.aston.domain.repository.CharactersRemoteRepository
import javax.inject.Inject

// Доработать
class FetchCharactersUseCase @Inject constructor(
    private val repository: CharactersRemoteRepository
) {

//    operator fun invoke(): Flow<List<CharacterDetails>> = repository.fetchCharacters()

}