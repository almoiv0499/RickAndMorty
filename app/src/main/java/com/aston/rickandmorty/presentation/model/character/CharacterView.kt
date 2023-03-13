package com.aston.rickandmorty.presentation.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterView(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationView,
    val name: String,
    val origin: OriginView,
    val species: String,
    val status: String
) : Parcelable