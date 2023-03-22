package com.aston.data.model.episode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes_table")
data class EpisodeInfoData(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    @PrimaryKey val id: Int,
    val name: String,
    val url: String,
)