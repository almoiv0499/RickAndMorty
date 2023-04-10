package com.aston.data.database.datasource

import androidx.paging.PagingSource
import com.aston.data.database.dao.CharactersDao
import com.aston.data.model.character.CharacterInfoData
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    private val dao: CharactersDao,
) {

    fun fetchCharacters(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): PagingSource<Int, CharacterInfoData> {
        return dao.fetchCharacters(
            characterName, characterStatus, characterSpecies, characterGender
        )
    }

    fun fetchCharactersById(characterIds: List<Int>): Flow<List<CharacterInfoData>> {
        return dao.fetchCharactersById(characterIds)
    }

    fun fetchCharactersByIdForEpisode(characterIds: List<Int>): Observable<List<CharacterInfoData>> {
        return dao.fetchCharactersByIdForEpisode(characterIds)
    }

    suspend fun insertCharacters(characters: List<CharacterInfoData>) {
        dao.insertCharacters(characters)
    }

    fun insertCharactersForEpisode(characters: List<CharacterInfoData>) {
        dao.insertCharactersForEpisode(characters)
    }

}