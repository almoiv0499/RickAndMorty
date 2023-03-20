package com.aston.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aston.data.model.character.CharacterInfoData

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters_table WHERE name LIKE '%' || :characterName || '%' " +
            "AND status LIKE '%' || :characterStatus || '%' " +
            "AND species LIKE '%' || :characterSpecies || '%' " +
            "AND gender LIKE '%' || :characterGender || '%'")
    fun fetchCharacters(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String
    ): PagingSource<Int, CharacterInfoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterInfoData>)

    @Query("DELETE FROM characters_table")
    suspend fun deleteCharacters()
}