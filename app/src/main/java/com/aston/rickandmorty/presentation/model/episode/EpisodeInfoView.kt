package com.aston.rickandmorty.presentation.model.episode

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeInfoView(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episodeNumber: String,
    val episodeId: Int,
    val episodeName: String,
) : Parcelable