package com.aston.data.model

import com.google.gson.annotations.SerializedName

data class AllCharactersData(
    val info: InfoData,
    @SerializedName("results") val characterInfo: List<CharacterInfoData>
)