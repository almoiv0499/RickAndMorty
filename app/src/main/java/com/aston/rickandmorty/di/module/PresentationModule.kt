package com.aston.rickandmorty.di.module

import com.aston.rickandmorty.presentation.activity.router.RouterMainActivity
import com.aston.rickandmorty.presentation.mapper.MapperView
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideMapperView(): MapperView = MapperView()

    @Provides
    fun provideRouter(): RouterMainActivity = RouterMainActivity

}