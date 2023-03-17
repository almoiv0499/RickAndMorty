package com.aston.rickandmorty.di.module

import android.app.Application
import androidx.room.Room
import com.aston.data.database.ApplicationDatabase
import com.aston.data.database.CharactersDao
import com.aston.data.remote.CharactersService
import com.aston.data.repository.CharactersRemoteRepositoryImpl
import com.aston.data.util.mapper.MapperCharacterData
import com.aston.data.util.mapper.MapperEpisodeData
import com.aston.domain.repository.CharactersRemoteRepository
import com.aston.rickandmorty.di.annotation.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @AppScope
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
    }

    @AppScope
    @Provides
    fun provideApplicationDatabase(application: Application): ApplicationDatabase =
        Room.databaseBuilder(application, ApplicationDatabase::class.java, "database")
            .build()

    @AppScope
    @Provides
    fun provideCharactersDao(applicationDatabase: ApplicationDatabase): CharactersDao =
        applicationDatabase.charactersDao()

    @AppScope
    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)

    @Provides
    fun provideCharacterRemoteRepository(
        service: CharactersService,
        database: ApplicationDatabase,
        mapperEpisode: MapperEpisodeData,
        mapperCharacter: MapperCharacterData,
    ): CharactersRemoteRepository = CharactersRemoteRepositoryImpl(
        service, database, mapperEpisode, mapperCharacter
    )

}