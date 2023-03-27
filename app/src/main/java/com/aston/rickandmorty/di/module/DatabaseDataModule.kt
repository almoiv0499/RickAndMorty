package com.aston.rickandmorty.di.module

import android.content.Context
import androidx.room.Room
import com.aston.data.database.ApplicationDatabase
import com.aston.data.database.dao.CharactersDao
import com.aston.data.database.dao.EpisodeDao
import com.aston.data.database.dao.LocationDao
import com.aston.rickandmorty.di.annotation.AppScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseDataModule {

    @AppScope
    @Provides
    fun provideApplicationDatabase(context: Context): ApplicationDatabase =
        Room.databaseBuilder(context, ApplicationDatabase::class.java, "database3").build()

    @AppScope
    @Provides
    fun provideCharactersDao(applicationDatabase: ApplicationDatabase): CharactersDao =
        applicationDatabase.charactersDao()

    @AppScope
    @Provides
    fun provideLocationsDao(applicationDatabase: ApplicationDatabase): LocationDao =
        applicationDatabase.locationDao()

    @AppScope
    @Provides
    fun provideEpisodesDao(applicationDatabase: ApplicationDatabase): EpisodeDao =
        applicationDatabase.episodeDao()

}