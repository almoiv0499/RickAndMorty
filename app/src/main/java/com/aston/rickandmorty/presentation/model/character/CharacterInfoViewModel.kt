package com.aston.rickandmorty.presentation.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterInfoViewModel(
    val created: String,
    val episodes: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationViewModel,
    val name: String,
    val origin: OriginViewModel,
    val species: String,
    val status: String,
) : Parcelable