package com.aston.domain.usecase.location

import com.aston.domain.repository.LocationRepository
import javax.inject.Inject

class FetchCharactersByIdUseCase @Inject constructor(
    private val repository: LocationRepository,
) {

    operator fun invoke(characterIds: List<Int>) = repository.fetchCharactersById(characterIds)

}