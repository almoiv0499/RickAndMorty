package com.aston.domain.usecase

import com.aston.domain.model.episode.Episode
import com.aston.domain.repository.CharactersRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchEpisodesByIdsUseCase @Inject constructor(
    private val repository: CharactersRemoteRepository
) {

    operator fun invoke(episodeIds: List<Int>): Flow<List<Episode>> =
        repository.fetchEpisodesByIdsUseCase(episodeIds)

}