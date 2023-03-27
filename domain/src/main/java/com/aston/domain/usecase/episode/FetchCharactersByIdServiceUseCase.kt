package com.aston.domain.usecase.episode

import com.aston.domain.repository.EpisodeRepository
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FetchCharactersByIdServiceUseCase @Inject constructor(
    private val repository: EpisodeRepository,
) {

    operator fun invoke(characterIds: List<Int>) =
        repository.fetchCharactersByIdService(characterIds).subscribeOn(
            Schedulers.io()
        )

}