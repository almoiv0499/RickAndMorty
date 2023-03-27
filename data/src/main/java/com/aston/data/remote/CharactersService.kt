package com.aston.data.remote

import com.aston.data.model.character_dto.CharacterResultDataDto
import com.aston.data.model.episode_dto.EpisodeInfoDataDto
import com.aston.data.model.location_dto.LocationInfoDataDto
import com.aston.data.model.location_dto.LocationResultDataDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {

    @GET("api/character")
    suspend fun fetchCharactersByPage(
        @Query("page") page: Int,
        @Query("name") characterName: String,
        @Query("status") characterStatus: String,
        @Query("species") characterSpecies: String,
        @Query("gender") characterGender: String,
    ): CharacterResultDataDto

    @GET("api/episode/{episodeIds}")
    suspend fun fetchEpisodesByIds(
        @Path("episodeIds") episodeIds: List<Int>,
    ): List<EpisodeInfoDataDto>

    @GET("api/location/{locationId}")
    suspend fun fetchLocationById(
        @Path("locationId") locationId: Int,
    ): LocationInfoDataDto

    @GET("api/location")
    suspend fun fetchOriginLocationByName(
        @Query("name") originLocationName: String,
    ): LocationResultDataDto

}