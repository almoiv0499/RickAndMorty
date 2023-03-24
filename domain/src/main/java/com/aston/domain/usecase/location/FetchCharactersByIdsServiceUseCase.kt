package com.aston.domain.usecase.location

import com.aston.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCharactersByIdUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(characterIds: List<Int>) =
        repository.fetchCharactersById(characterIds).flowOn(ioDispatcher)

}