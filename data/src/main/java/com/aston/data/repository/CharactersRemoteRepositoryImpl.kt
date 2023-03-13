package com.aston.data.repository

import com.aston.data.remote.CharactersService
import com.aston.data.util.mapper.MapperCharacterData
import com.aston.data.util.mapper.MapperEpisodeData
import com.aston.domain.model.character.CharactersResult
import com.aston.domain.model.episode.Episode
import com.aston.domain.repository.CharactersRemoteRepository
import javax.inject.Inject

class CharactersRemoteRepositoryImpl @Inject constructor(
    private val charactersService: CharactersService,
    private val mapperCharacter: MapperCharacterData,
    private val mapperEpisode: MapperEpisodeData,
) : CharactersRemoteRepository {

    override suspend fun getAllCharacters(): CharactersResult =
        mapperCharacter.mapToCharactersResult(charactersService.getAllCharacters())

    override suspend fun getEpisodesForCharacterByUrl(episodeIds: List<Int>): List<Episode> {
        return charactersService.getEpisodesForCharacterByUrl(episodeIds).map { episode ->
            mapperEpisode.mapToEpisode(episode)
        }
    }
}