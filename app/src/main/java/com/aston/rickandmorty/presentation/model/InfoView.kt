package com.aston.rickandmorty.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoView(
    val count: Int,
    val next: String,
    val pages: Int
) : Parcelable