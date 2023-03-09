package com.aston.domain.repository

import com.aston.domain.model.AllCharacters

interface CharactersRemoteRepository {

    suspend fun getAllCharacters(): AllCharacters

}