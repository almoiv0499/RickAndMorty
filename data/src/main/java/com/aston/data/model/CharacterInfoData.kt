package com.aston.data.model

data class CharacterInfoData(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationData,
    val name: String,
    val origin: OriginData,
    val species: String,
    val status: String
)