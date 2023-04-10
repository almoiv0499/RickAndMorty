package com.aston.data.remote

import com.aston.data.model.character_dto.CharacterInfoDataDto
import com.aston.data.model.location_dto.LocationResultDataDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationsService {

    @GET("api/location")
    suspend fun fetchLocationsByPage(
        @Query("page") page: Int,
        @Query("name") locationName: String,
        @Query("type") locationType: String,
        @Query("dimension") locationDimension: String,
    ): LocationResultDataDto

    @GET("api/character/{characterIds}")
    suspend fun fetchCharactersByIds(
        @Path("characterIds") characterIds: List<Int>,
    ): List<CharacterInfoDataDto>

}