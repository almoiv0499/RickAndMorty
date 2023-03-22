package com.aston.rickandmorty.di.module

import com.aston.domain.repository.CharactersRepository
import com.aston.domain.usecase.FetchCharactersThoughDatabaseUseCase
import com.aston.domain.usecase.FetchCharactersThoughServiceUseCase
import com.aston.domain.usecase.FetchEpisodesByIdsUseCase
import dagger.Module
import dagger.Provides

// Понял, что пока не нужен
@Module
class DomainModule {

//    @Provides
//    fun provideFetchCharactersThoughDatabaseUseCase(
//        repository: CharactersRepository
//    ): FetchCharactersThoughDatabaseUseCase = FetchCharactersThoughDatabaseUseCase(repository)
//
//    @Provides
//    fun provideFetchCharactersServiceUseCase(
//        repository: CharactersRepository
//    ): FetchCharactersThoughServiceUseCase = FetchCharactersThoughServiceUseCase(repository)
//
//    @Provides
//    fun provideFetchEpisodesForCharactersByIds(
//        repository: CharactersRepository
//    ): FetchEpisodesByIdsUseCase = FetchEpisodesByIdsUseCase(repository)

}