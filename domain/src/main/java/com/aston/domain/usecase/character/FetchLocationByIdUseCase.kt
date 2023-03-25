package com.aston.domain.usecase.character

import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchLocationByIdUseCase @Inject constructor(
    private val repository: CharactersRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(locationId: Int) =
        repository.fetchLocationById(locationId).flowOn(ioDispatcher)

}