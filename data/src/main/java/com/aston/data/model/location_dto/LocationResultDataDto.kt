package com.aston.data.model.location_dto

import com.aston.domain.model.location.Info
import com.google.gson.annotations.SerializedName

data class LocationResultDataDto(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val locations: List<LocationInfoDataDto>
)
