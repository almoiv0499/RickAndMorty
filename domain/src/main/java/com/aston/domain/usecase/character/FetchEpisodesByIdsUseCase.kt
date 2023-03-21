package com.aston.domain.usecase.character

import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchEpisodesByIdsUseCase @Inject constructor(
    private val repository: CharactersRepository
) {

    operator fun invoke(episodeIds: List<Int>): Flow<List<EpisodeInfo>> =
        repository.fetchEpisodesByIds(episodeIds)

}