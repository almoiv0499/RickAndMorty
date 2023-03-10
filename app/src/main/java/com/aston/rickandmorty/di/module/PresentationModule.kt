package com.aston.rickandmorty.di.module

import com.aston.rickandmorty.presentation.mapper.MapperView
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideMapperView(): MapperView = MapperView()

}