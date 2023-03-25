package com.aston.domain.usecase.character

import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchOriginLocationByNameUseCase @Inject constructor(
    private val repository: CharactersRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(originLocationName: String) =
        repository.fetchOriginLocationByName(originLocationName).flowOn(ioDispatcher)

}