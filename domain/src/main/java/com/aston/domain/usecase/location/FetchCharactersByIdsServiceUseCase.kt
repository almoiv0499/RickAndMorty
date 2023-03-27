package com.aston.domain.usecase.location

import com.aston.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCharactersByIdsUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(characterIds: List<Int>) =
        repository.fetchCharactersByIds(characterIds).flowOn(ioDispatcher)

}