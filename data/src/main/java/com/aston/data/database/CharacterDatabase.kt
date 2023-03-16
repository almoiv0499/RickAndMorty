package com.aston.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aston.data.model.character.CharacterData
import com.aston.data.model.episode.EpisodeData

@Database(entities = [CharacterData::class, EpisodeData::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun characterDao(): CharactersDao

    abstract fun episodeDao(): EpisodeDao

}