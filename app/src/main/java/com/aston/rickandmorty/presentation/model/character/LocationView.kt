package com.aston.rickandmorty.presentation.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationView(
    val locationName: String,
    val locationInfo: String
) : Parcelable