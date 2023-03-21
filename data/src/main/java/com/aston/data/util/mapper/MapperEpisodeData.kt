package com.aston.data.util.mapper

import com.aston.data.model.episode.EpisodeInfoData
import com.aston.domain.model.episode.EpisodeInfo
import javax.inject.Inject

class MapperEpisodeData @Inject constructor() {

    fun mapToEpisode(episode: EpisodeInfoData): EpisodeInfo {
        return EpisodeInfo(
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