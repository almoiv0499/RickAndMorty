package com.aston.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aston.data.model.character.CharacterInfoData
import com.aston.data.model.episode.EpisodeInfoData
import com.aston.data.model.location.LocationInfoData

@Database(
    entities = [CharacterInfoData::class, EpisodeInfoData::class, LocationInfoData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    abstract fun locationDao(): LocationDao

    abstract fun episodeDao(): EpisodeDao

}