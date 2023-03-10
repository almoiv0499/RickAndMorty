package com.aston.rickandmorty.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllCharactersView(
    val info: InfoView,
    val characterInfo: List<CharacterInfoView>
) : Parcelable