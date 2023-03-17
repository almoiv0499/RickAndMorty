package com.aston.rickandmorty.di.module

import com.aston.domain.repository.CharactersRemoteRepository
import com.aston.domain.usecase.FetchCharactersThoughDatabaseUseCase
import com.aston.domain.usecase.FetchCharactersThoughServiceUseCase
import com.aston.domain.usecase.FetchEpisodesByIdsUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideFetchCharactersThoughDatabaseUseCase(
        repository: CharactersRemoteRepository
    ): FetchCharactersThoughDatabaseUseCase = FetchCharactersThoughDatabaseUseCase(repository)

    @Provides
    fun provideFetchCharactersServiceUseCase(
        repository: CharactersRemoteRepository
    ): FetchCharactersThoughServiceUseCase = FetchCharactersThoughServiceUseCase(repository)

    @Provides
    fun provideFetchEpisodesForCharactersByIds(
        repository: CharactersRemoteRepository
    ): FetchEpisodesByIdsUseCase = FetchEpisodesByIdsUseCase(repository)

}