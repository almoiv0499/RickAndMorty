package com.aston.data.model.character_dto

import com.aston.data.model.character.LocationData
import com.aston.data.model.character.OriginData
import com.google.gson.annotations.SerializedName

data class CharacterInfoDataDto(
    @SerializedName("created") val characterCreated: String,
    @SerializedName("episode") val episodes: List<String>,
    @SerializedName("gender") val characterGender: String,
    @SerializedName("id") val characterId: Int,
    @SerializedName("image") val characterImage: String,
    @SerializedName("location") val location: LocationDataDto,
    @SerializedName("name") val characterName: String,
    @SerializedName("origin") val origin: OriginDataDto,
    @SerializedName("species") val characterSpecies: String,
    @SerializedName("status") val characterStatus: String,
)