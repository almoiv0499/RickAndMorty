package com.aston.rickandmorty.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView
import javax.inject.Inject

class MapperEpisodeView @Inject constructor() {

    fun mapToEpisodePaging(paging: PagingData<EpisodeInfo>): PagingData<EpisodeInfoView> {
        return paging.map { episode ->
            mapToEpisodeView(episode)
        }
    }

    fun mapToEpisodeView(episode: EpisodeInfo): EpisodeInfoView {
        return EpisodeInfoView(
            airDate = episode.airDate,
            characters = episode.characters,
            created = episode.created,
            episodeNumber = episode.episodeNumber,
            episodeId = episode.id,
            episodeName = episode.name
        )
    }

}