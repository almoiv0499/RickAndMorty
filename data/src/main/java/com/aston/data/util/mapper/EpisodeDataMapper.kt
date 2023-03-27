package com.aston.data.util.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.data.model.episode.EpisodeInfoData
import com.aston.data.model.episode_dto.EpisodeInfoDataDto
import com.aston.domain.model.episode.EpisodeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EpisodeDataMapper @Inject constructor() {

    fun mapToEpisodesListData(list: List<EpisodeInfoDataDto>): List<EpisodeInfoData> {
        return list.map { episode ->
            mapToEpisodeData(episode)
        }
    }

    fun mapToEpisodeData(episode: EpisodeInfoDataDto): EpisodeInfoData {
        return EpisodeInfoData(
            air_date = episode.airDate,
            characters = episode.characters,
            created = episode.episodeCreated,
            episode = episode.episodeNumber,
            id = episode.episodeId,
            name = episode.episodeName,
            url = episode.url
        )
    }

    fun mapToPagingEpisodeInfo(pagingData: PagingData<EpisodeInfoData>): PagingData<EpisodeInfo> {
        return pagingData.map { episode ->
            mapToEpisode(episode)
        }
    }

    fun mapToEpisodeFlowList(flow: Flow<List<EpisodeInfoData>>): Flow<List<EpisodeInfo>> {
        return flow.map {episodes ->
            mapToEpisodesList(episodes)
        }
    }

    private fun mapToEpisodesList(list: List<EpisodeInfoData>): List<EpisodeInfo> {
        return list.map { episode ->
            mapToEpisode(episode)
        }
    }

    private fun mapToEpisode(episode: EpisodeInfoData): EpisodeInfo {
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