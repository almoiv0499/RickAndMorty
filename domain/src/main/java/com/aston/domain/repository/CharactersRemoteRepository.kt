package com.aston.domain.repository

import com.aston.domain.model.character.CharactersResult
import com.aston.domain.model.episode.Episode

interface CharactersRemoteRepository {

    suspend fun getAllCharacters(): CharactersResult

    suspend fun getEpisodesForCharacterByUrl(episodeIds: List<Int>): List<Episode>

}