package com.aston.rickandmorty.di.module

import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.di.annotation.ViewModelKey
import com.aston.rickandmorty.presentation.activity.main.MainViewModel
import com.aston.rickandmorty.presentation.fragment.characters.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(CharactersViewModel::class)]
    fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}