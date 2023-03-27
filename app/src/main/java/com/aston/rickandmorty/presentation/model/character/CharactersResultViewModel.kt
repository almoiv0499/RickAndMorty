package com.aston.rickandmorty.presentation.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersResultViewModel(
    val info: InfoViewModel,
    val characters: List<CharacterInfoViewModel>
) : Parcelable