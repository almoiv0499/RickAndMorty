package com.aston.data.remote

import com.aston.data.model.character.CharactersResultData
import com.aston.data.model.episode.EpisodeData
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersService {

    @GET("api/character")
    suspend fun getAllCharacters(): CharactersResultData

    @GET("api/episode/{episodeIds}")
    suspend fun getEpisodesForCharacterByUrl(
        @Path("episodeIds") episodeIds: List<Int>
    ): List<EpisodeData>

}