package com.aston.rickandmorty.presentation.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersResultView(
    val info: InfoView,
    val characters: List<CharacterInfoView>
) : Parcelable