package com.aston.data.model.character_dto

import com.google.gson.annotations.SerializedName

data class LocationDataDto(
    @SerializedName("name") val locationName: String,
    @SerializedName("url") val locationInfo: String
)
