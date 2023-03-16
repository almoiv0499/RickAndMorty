package com.aston.data.model.character

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_table")
data class CharacterData(
    val created: String,
    val episode: List<String>,
    val gender: String,
    @PrimaryKey val id: Int,
    val image: String,
    @Embedded val location: LocationData,
    val name: String,
    @Embedded val origin: OriginData,
    val species: String,
    val status: String
)