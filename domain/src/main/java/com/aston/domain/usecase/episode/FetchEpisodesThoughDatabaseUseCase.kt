package com.aston.domain.usecase.episode

import com.aston.domain.repository.EpisodeRepository
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FetchEpisodesThoughDatabaseUseCase @Inject constructor(
    private val repository: EpisodeRepository,
) {

    operator fun invoke(episodeName: String, episodeNumber: String) =
        repository.fetchEpisodesThoughDatabase(episodeName, episodeNumber)
            .subscribeOn(Schedulers.io())

}