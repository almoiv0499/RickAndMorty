package com.aston.domain.usecase.character

import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchLocationByIdServiceUseCase @Inject constructor(
    private val repository: CharactersRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(locationId: Int) = withContext(ioDispatcher) {
        repository.fetchLocationByIdService(locationId)
    }

}