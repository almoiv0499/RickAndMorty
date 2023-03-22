package com.aston.domain.usecase.episode

import com.aston.domain.repository.EpisodeRepository
import javax.inject.Inject

class FetchEpisodeThoughDatabaseUseCase @Inject constructor(
    private val repository: EpisodeRepository,
) {

    operator fun invoke(episodeName: String, episodeNumber: String) =
        repository.fetchEpisodesThoughDatabase(episodeName, episodeNumber)

}