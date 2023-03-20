package com.aston.rickandmorty.di.module

import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.di.annotation.ViewModelKey
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsViewModel
import com.aston.rickandmorty.presentation.fragment.characters.CharactersViewModel
import com.aston.rickandmorty.presentation.fragment.characters_filter.CharactersFilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(CharactersViewModel::class)]
    fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(CharacterDetailsViewModel::class)]
    fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(CharactersFilterViewModel::class)]
    fun bindCharactersFilterViewModel(viewModel: CharactersFilterViewModel): ViewModel

}