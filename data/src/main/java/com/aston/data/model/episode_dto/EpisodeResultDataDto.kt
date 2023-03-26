package com.aston.data.model.episode_dto

import com.aston.data.model.episode.InfoData
import com.google.gson.annotations.SerializedName

data class EpisodeResultDataDto(
    @SerializedName("info") val info: InfoData,
    @SerializedName("results") val episodes: List<EpisodeInfoDataDto>
)
