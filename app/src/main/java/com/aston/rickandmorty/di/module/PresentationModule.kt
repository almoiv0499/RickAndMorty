package com.aston.rickandmorty.di.module

import com.aston.rickandmorty.presentation.activity.router.MainActivityRouter
import com.aston.rickandmorty.presentation.activity.router.MainActivityRouterImpl
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideRouter(): MainActivityRouter = MainActivityRouterImpl

}