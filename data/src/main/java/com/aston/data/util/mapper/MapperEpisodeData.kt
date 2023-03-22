package com.aston.data.util.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.data.model.episode.EpisodeInfoData
import com.aston.domain.model.episode.EpisodeInfo
import javax.inject.Inject

class MapperEpisodeData @Inject constructor() {

    fun mapToPagingLocationInfo(pagingData: PagingData<EpisodeInfoData>): PagingData<EpisodeInfo> {
        return pagingData.map { episode ->
            mapToEpisode(episode)
        }
    }

    fun mapToEpisode(episode: EpisodeInfoData): EpisodeInfo {
        return EpisodeInfo(
            airDate = episode.air_date,
            characters = episode.characters,
            created = episode.created,
            episodeNumber = episode.episode,
            id = episode.id,
            name = episode.name,
            url = episode.url
        )
    }

}