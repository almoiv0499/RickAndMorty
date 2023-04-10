package com.aston.data.model.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class LocationInfoData(
    val created: String,
    val dimension: String,
    @PrimaryKey val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)