package com.aston.rickandmorty.di.module

import com.aston.data.remote.CharactersService
import com.aston.data.remote.EpisodeService
import com.aston.data.remote.LocationsService
import com.aston.rickandmorty.di.annotation.AppScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ServiceDataModule {

    @AppScope
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
    }

    @AppScope
    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)

    @AppScope
    @Provides
    fun provideLocationsService(retrofit: Retrofit): LocationsService =
        retrofit.create(LocationsService::class.java)

    @AppScope
    @Provides
    fun provideEpisodesService(retrofit: Retrofit): EpisodeService =
        retrofit.create(EpisodeService::class.java)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

}