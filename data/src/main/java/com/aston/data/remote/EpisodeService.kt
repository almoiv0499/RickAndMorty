package com.aston.data.remote

import com.aston.data.model.character_dto.CharacterInfoDataDto
import com.aston.data.model.episode_dto.EpisodeResultDataDto
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeService {

    @GET("api/episode")
    fun fetchEpisodesByPage(
        @Query("page") page: Int,
        @Query("name") episodeName: String,
        @Query("episode") episodeNumber: String
    ): Single<EpisodeResultDataDto>

    @GET("api/character/{characterIds}")
    fun fetchCharactersByIds(
        @Path("characterIds") characterIds: List<Int>,
    ): Observable<List<CharacterInfoDataDto>>

}