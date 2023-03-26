package com.aston.data.model.location_dto

import com.google.gson.annotations.SerializedName

data class LocationInfoDataDto(
    @SerializedName("created") val locationCreated: String,
    @SerializedName("dimension") val locationDimension: String,
    @SerializedName("id") val locationId: Int,
    @SerializedName("name") val locationName: String,
    @SerializedName("residents") val characters: List<String>,
    @SerializedName("type") val locationType: String,
    @SerializedName("url") val url: String
)