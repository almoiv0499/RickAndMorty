package com.aston.rickandmorty.presentation.model.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationResultView(
    val info: InfoView,
    val results: List<LocationInfoView>,
) : Parcelable