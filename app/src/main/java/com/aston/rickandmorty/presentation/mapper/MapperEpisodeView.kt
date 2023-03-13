package com.aston.rickandmorty.presentation.mapper

import com.aston.domain.model.episode.Episode
import com.aston.rickandmorty.presentation.model.episode.EpisodeView
import javax.inject.Inject

class MapperEpisodeView @Inject constructor() {

    fun mapToEpisodeView(episode: Episode): EpisodeView {
        return EpisodeView(
            air_date = episode.air_date,
            characters = episode.characters,
            created = episode.created,
            episode = episode.episode,
            id = episode.id,
            name = episode.name,
            url = episode.url
        )
    }

}