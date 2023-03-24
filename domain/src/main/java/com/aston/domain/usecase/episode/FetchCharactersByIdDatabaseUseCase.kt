package com.aston.domain.usecase.episode

import com.aston.domain.repository.EpisodeRepository
import javax.inject.Inject

class FetchCharactersByIdDatabaseUseCase @Inject constructor(
    private val repository: EpisodeRepository,
) {

    operator fun invoke(characterIds: List<Int>) =
        repository.fetchCharactersByIdDatabase(characterIds)

}