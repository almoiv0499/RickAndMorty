package com.aston.data.model.location

import com.aston.domain.model.location.Info
import com.aston.domain.model.location.LocationInfo

data class LocationResultData(
    val info: Info,
    val results: List<LocationInfo>
)