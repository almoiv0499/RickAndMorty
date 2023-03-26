package com.aston.domain.usecase.character

import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchOriginLocationByNameDatabaseUseCase @Inject constructor(
    private val repository: CharactersRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(originLocationName: String) =
        repository.fetchOriginLocationByNameDatabase(originLocationName).flowOn(ioDispatcher)

}