package com.aston.data.model.episode_dto

import com.google.gson.annotations.SerializedName

data class EpisodeInfoDataDto(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("characters") val characters: List<String>,
    @SerializedName("created") val episodeCreated: String,
    @SerializedName("episode") val episodeNumber: String,
    @SerializedName("id") val episodeId: Int,
    @SerializedName("name") val episodeName: String,
    @SerializedName("url") val url: String,
)
