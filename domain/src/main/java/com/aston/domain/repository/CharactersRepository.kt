package com.aston.domain.repository

import androidx.paging.PagingData
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.model.location.LocationInfo
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun fetchCharactersThoughService(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfo>>

    fun fetchCharactersThoughDatabase(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfo>>

    fun fetchEpisodesByIds(episodeIds: List<Int>): Flow<List<EpisodeInfo>>

    suspend fun fetchLocationByIdService(locationId: Int): LocationInfo
    fun fetchLocationByIdDatabase(locationId: Int): Flow<LocationInfo>

    suspend fun fetchOriginLocationByNameService(originLocationName: String): LocationInfo
    fun fetchOriginLocationByNameDatabase(originLocationName: String): Flow<LocationInfo>

}