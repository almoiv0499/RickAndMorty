package com.aston.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aston.data.model.character.CharacterData

@Database(entities = [CharacterData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun characterDao(): CharactersDao

}