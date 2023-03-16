package com.aston.rickandmorty.di.module

import com.aston.rickandmorty.presentation.activity.router.RouterMainActivity
import com.aston.rickandmorty.presentation.activity.router.RouterMainActivityImpl
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideMapperView(): MapperCharacterView = MapperCharacterView()

    @Provides
    fun provideRouter(): RouterMainActivity = RouterMainActivityImpl

}