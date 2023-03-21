package com.aston.domain.usecase.character

import com.aston.domain.repository.CharactersRepository
import javax.inject.Inject


class FetchCharactersThoughServiceUseCase @Inject constructor(
    private val repository: CharactersRepository,
) {

    operator fun invoke(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ) = repository.fetchCharactersThoughService(
        characterName, characterStatus, characterSpecies, characterGender
    )

}