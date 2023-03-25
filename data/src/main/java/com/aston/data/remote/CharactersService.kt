package com.aston.data.remote

import com.aston.data.model.character.CharactersResultData
import com.aston.data.model.episode.EpisodeInfoData
import com.aston.data.model.location.LocationInfoData
import com.aston.data.model.location.LocationResultData
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
    ): CharactersResultData

    @GET("api/episode/{episodeIds}")
    suspend fun getEpisodesForCharacterByUrl(
        @Path("episodeIds") episodeIds: List<Int>,
    ): List<EpisodeInfoData>

    @GET("api/location/{locationId}")
    fun fetchLocationById(
        @Path("locationId") locationId: Int,
    ): LocationResultData

    @GET("api/location")
    fun fetchOriginLocationByName(
        @Query("name") originLocationName: String,
    ): LocationResultData

}