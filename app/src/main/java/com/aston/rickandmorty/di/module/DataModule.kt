package com.aston.rickandmorty.di.module

import com.aston.data.mapper.MapperData
import com.aston.data.remote.CharactersService
import com.aston.data.repository.CharactersRemoteRepositoryImpl
import com.aston.domain.repository.CharactersRemoteRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://rickandmortyapi.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)

    @Provides
    fun provideMapperData(): MapperData = MapperData()

    @Provides
    fun provideCharacterRemoteRepository(
        charactersService: CharactersService,
        mapper: MapperData
    ): CharactersRemoteRepository = CharactersRemoteRepositoryImpl(charactersService, mapper)

}