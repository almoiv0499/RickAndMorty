package com.aston.data.remote

import com.aston.data.model.character.CharactersResultData
import com.aston.data.model.episode.EpisodeData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {

    @GET("api/character")
    suspend fun fetchCharacters(): Response<CharactersResultData>

    @GET("api/character")
    suspend fun fetchCharactersByPage(
        @Query("page") page: Int
    ): CharactersResultData

    @GET("api/episode/{episodeIds}")
    suspend fun getEpisodesForCharacterByUrl(
        @Path("episodeIds") episodeIds: List<Int>
    ): List<EpisodeData>

}