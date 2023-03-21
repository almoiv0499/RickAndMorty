package com.aston.rickandmorty.di.module

import com.aston.domain.repository.CharactersRepository
import com.aston.domain.repository.LocationRepository
import com.aston.domain.usecase.character.FetchCharactersThoughDatabaseUseCase
import com.aston.domain.usecase.character.FetchCharactersThoughServiceUseCase
import com.aston.domain.usecase.character.FetchEpisodesByIdsUseCase
import com.aston.domain.usecase.location.FetchLocationsThoughDatabaseUseCase
import com.aston.domain.usecase.location.FetchLocationsThoughServiceUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideFetchCharactersThoughDatabaseUseCase(
        repository: CharactersRepository,
    ): FetchCharactersThoughDatabaseUseCase = FetchCharactersThoughDatabaseUseCase(repository)

    @Provides
    fun provideFetchCharactersServiceUseCase(
        repository: CharactersRepository,
    ): FetchCharactersThoughServiceUseCase = FetchCharactersThoughServiceUseCase(repository)

    @Provides
    fun provideFetchEpisodesForCharactersByIds(
        repository: CharactersRepository,
    ): FetchEpisodesByIdsUseCase = FetchEpisodesByIdsUseCase(repository)

    @Provides
    fun provideFetchLocationsThoughDatabaseUseCase(
        repository: LocationRepository,
    ): FetchLocationsThoughDatabaseUseCase = FetchLocationsThoughDatabaseUseCase(repository)

    @Provides
    fun provideFetchLocationsServiceUseCase(
        repository: LocationRepository,
    ): FetchLocationsThoughServiceUseCase = FetchLocationsThoughServiceUseCase(repository)

}