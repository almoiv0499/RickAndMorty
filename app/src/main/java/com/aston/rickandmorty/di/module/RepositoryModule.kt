package com.aston.rickandmorty.di.module

import com.aston.data.repository.CharactersRepositoryImpl
import com.aston.data.repository.EpisodeRepositoryImpl
import com.aston.data.repository.LocationsRepositoryImpl
import com.aston.domain.repository.CharactersRepository
import com.aston.domain.repository.EpisodeRepository
import com.aston.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(repositoryImpl: CharactersRepositoryImpl): CharactersRepository

    @Binds
    fun bindLocationRepository(repositoryImpl: LocationsRepositoryImpl): LocationRepository

    @Binds
    fun bindEpisodeRepository(repositoryImpl: EpisodeRepositoryImpl): EpisodeRepository

}