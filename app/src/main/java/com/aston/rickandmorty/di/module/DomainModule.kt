package com.aston.rickandmorty.di.module

import com.aston.domain.repository.CharactersRemoteRepository
import com.aston.domain.usecase.FetchAndCacheCharactersByPageUseCase
import com.aston.domain.usecase.FetchCharactersUseCase
import com.aston.domain.usecase.GetEpisodesForCharacterByUrlUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideFetchCharactersUseCase(
        repository: CharactersRemoteRepository,
    ): FetchCharactersUseCase = FetchCharactersUseCase(repository)

    @Provides
    fun provideGetEpisodesForCharactersByUrl(
        repository: CharactersRemoteRepository,
        ioDispatcher: CoroutineDispatcher,
    ): GetEpisodesForCharacterByUrlUseCase =
        GetEpisodesForCharacterByUrlUseCase(repository, ioDispatcher)

    @Provides
    fun provideFetchAndCacheCharactersByPageUseCase(
        repository: CharactersRemoteRepository,
    ): FetchAndCacheCharactersByPageUseCase = FetchAndCacheCharactersByPageUseCase(repository)

}