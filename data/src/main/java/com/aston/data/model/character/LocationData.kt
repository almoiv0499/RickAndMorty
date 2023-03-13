package com.aston.data.model.character

import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("name") val locationName: String,
    @SerializedName("url") val locationInfo: String
)