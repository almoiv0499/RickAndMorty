package com.aston.domain.usecase

import com.aston.domain.model.episode.Episode
import com.aston.domain.repository.CharactersRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetEpisodesForCharacterByUrlUseCase @Inject constructor(
    private val repository: CharactersRemoteRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(episodeIds: List<Int>): List<Episode> = withContext(ioDispatcher) {
        repository.getEpisodesForCharacterByUrl(episodeIds)
    }

}