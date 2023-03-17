package com.aston.data.model.character

import com.google.gson.annotations.SerializedName

data class CharactersResultData(
    val info: InfoData,
    @SerializedName("results") val characterInfo: List<CharacterInfoData>
)