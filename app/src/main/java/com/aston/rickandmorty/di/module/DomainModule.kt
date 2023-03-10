package com.aston.rickandmorty.di.module

import com.aston.domain.repository.CharactersRemoteRepository
import com.aston.domain.usecase.GetAllCharactersUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideGetAllCharactersUseCase(
        repository: CharactersRemoteRepository,
        ioDispatcher: CoroutineDispatcher
    ) : GetAllCharactersUseCase = GetAllCharactersUseCase(repository, ioDispatcher)

}