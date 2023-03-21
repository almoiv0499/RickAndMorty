package com.aston.rickandmorty.presentation.mapper

import com.aston.domain.model.episode.EpisodeInfo
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView
import javax.inject.Inject

class MapperEpisodeView @Inject constructor() {

    fun mapToEpisodeView(episode: EpisodeInfo): EpisodeInfoView {
        return EpisodeInfoView(
            airDate = episode.air_date,
            characters = episode.characters,
            created = episode.created,
            episodeNumber = episode.episode,
            episodeId = episode.id,
            episodeName = episode.name
        )
    }

}