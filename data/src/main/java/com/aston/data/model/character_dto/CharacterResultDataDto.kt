package com.aston.data.model.character_dto

import com.aston.data.model.character.InfoData
import com.google.gson.annotations.SerializedName

data class CharacterResultDataDto(
    val info: InfoData,
    @SerializedName("results") val characters: List<CharacterInfoDataDto>
)
