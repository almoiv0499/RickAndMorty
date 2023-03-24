package com.aston.domain.model.episode

data class EpisodeInfo(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episodeNumber: String,
    val id: Int,
    val name: String,
    val url: String
)