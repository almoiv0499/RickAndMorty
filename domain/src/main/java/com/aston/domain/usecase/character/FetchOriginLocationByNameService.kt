package com.aston.domain.usecase.character

import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchOriginLocationByNameService @Inject constructor(
    private val repository: CharactersRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(locationName: String) = withContext(ioDispatcher) {
        repository.fetchOriginLocationByNameService(locationName)
    }

}