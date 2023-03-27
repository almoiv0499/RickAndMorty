package com.aston.rickandmorty.presentation.model.episode

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeResultViewModel(
    val info: InfoViewModel,
    val results: List<EpisodeInfoViewModel>,
) : Parcelable