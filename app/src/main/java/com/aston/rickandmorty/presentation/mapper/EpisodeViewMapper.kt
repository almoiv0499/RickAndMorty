package com.aston.rickandmorty.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoViewModel
import javax.inject.Inject

class EpisodeViewMapper @Inject constructor() {

    fun mapToEpisodeListView(list: List<EpisodeInfo>): List<EpisodeInfoViewModel> {
        return list.map { episode ->
            mapToEpisodeView(episode)
        }
    }

    fun mapToEpisodePagingView(paging: PagingData<EpisodeInfo>): PagingData<EpisodeInfoViewModel> {
        return paging.map { episode ->
            mapToEpisodeView(episode)
        }
    }

    private fun mapToEpisodeView(episode: EpisodeInfo): EpisodeInfoViewModel {
        return EpisodeInfoViewModel(
            airDate = episode.airDate,
            characters = episode.characters,
            created = episode.created,
            episodeNumber = episode.episodeNumber,
            episodeId = episode.id,
            episodeName = episode.name
        )
    }

}