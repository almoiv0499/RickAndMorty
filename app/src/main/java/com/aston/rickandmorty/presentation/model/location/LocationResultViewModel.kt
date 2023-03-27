package com.aston.rickandmorty.presentation.model.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationResultViewModel(
    val info: InfoViewModel,
    val results: List<LocationInfoViewModel>,
) : Parcelable