package com.aston.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aston.data.model.character.CharacterInfoData

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters_table")
    fun fetchCharacters(): PagingSource<Int, CharacterInfoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterInfoData>)

    @Query("DELETE FROM characters_table")
    suspend fun deleteCharacters()
}