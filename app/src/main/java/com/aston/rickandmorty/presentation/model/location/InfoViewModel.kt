package com.aston.rickandmorty.presentation.model.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoViewModel(
    val count: Int,
    val next: String,
    val pages: Int,
) : Parcelable