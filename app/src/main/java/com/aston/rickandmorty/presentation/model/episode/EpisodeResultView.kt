package com.aston.rickandmorty.presentation.model.episode

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeResultView(
    val info: InfoView,
    val results: List<EpisodeInfoView>,
) : Parcelable