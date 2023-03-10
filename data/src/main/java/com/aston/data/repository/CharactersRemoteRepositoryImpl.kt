package com.aston.data.repository

import com.aston.data.mapper.MapperData
import com.aston.data.remote.CharactersService
import com.aston.domain.model.AllCharacters
import com.aston.domain.repository.CharactersRemoteRepository
import javax.inject.Inject

class CharactersRemoteRepositoryImpl @Inject constructor(
    private val charactersService: CharactersService,
    private val mapper: MapperData,
) : CharactersRemoteRepository {

    override suspend fun getAllCharacters(): AllCharacters =
        mapper.mapToAllCharacters(charactersService.getAllCharacters())
}