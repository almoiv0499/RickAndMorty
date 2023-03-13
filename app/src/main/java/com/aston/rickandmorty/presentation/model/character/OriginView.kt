package com.aston.rickandmorty.presentation.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OriginView(
    val originLocationName: String,
    val originLocationInfo: String
) : Parcelable