package com.aston.data.model.character

import com.google.gson.annotations.SerializedName

data class OriginData(
    @SerializedName("name") val originLocationName: String,
    @SerializedName("url") val originLocationInfo: String
)