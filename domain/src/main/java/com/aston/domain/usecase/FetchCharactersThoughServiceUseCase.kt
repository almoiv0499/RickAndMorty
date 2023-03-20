package com.aston.domain.usecase

import com.aston.domain.repository.CharactersRemoteRepository
import javax.inject.Inject


class FetchCharactersThoughServiceUseCase @Inject constructor(
    private val repository: CharactersRemoteRepository,
) {

    operator fun invoke(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ) = repository.fetchCharactersThoughService(
        characterName, characterStatus, characterSpecies, characterGender
    )

}