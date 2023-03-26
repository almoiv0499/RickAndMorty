package com.aston.data.model.character_dto

import com.google.gson.annotations.SerializedName

data class OriginDataDto(
    @SerializedName("name") val originLocationName: String,
    @SerializedName("url") val originLocationInfo: String
)