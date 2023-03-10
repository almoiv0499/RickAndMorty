package com.aston.data.remote

import com.aston.data.model.AllCharactersData
import retrofit2.http.GET

interface CharactersService {

    @GET("/api/character")
    suspend fun getAllCharacters(): AllCharactersData

}