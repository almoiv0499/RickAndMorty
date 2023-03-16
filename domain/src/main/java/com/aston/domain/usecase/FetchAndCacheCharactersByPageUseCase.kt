package com.aston.domain.usecase

import com.aston.domain.repository.CharactersRemoteRepository
import javax.inject.Inject


// Доработать
class FetchAndCacheCharactersByPageUseCase @Inject constructor(
    private val repository: CharactersRemoteRepository
) {

//    operator fun invoke(page: Int) = repository.fetchAndCacheCharactersByPage(page)

}