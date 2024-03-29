package com.aston.rickandmorty.di.component

import android.content.Context
import com.aston.rickandmorty.di.annotation.AppScope
import com.aston.rickandmorty.di.module.*
import com.aston.rickandmorty.presentation.activity.main.MainActivity
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.fragment.characters.CharactersFragment
import com.aston.rickandmorty.presentation.fragment.characters_filter.CharactersFilterFragment
import com.aston.rickandmorty.presentation.fragment.episode_details.EpisodeDetailsFragment
import com.aston.rickandmorty.presentation.fragment.episode_filter.EpisodesFilterFragment
import com.aston.rickandmorty.presentation.fragment.episodes.EpisodesFragment
import com.aston.rickandmorty.presentation.fragment.location_details.LocationDetailsFragment
import com.aston.rickandmorty.presentation.fragment.location_filter.LocationsFilterFragment
import com.aston.rickandmorty.presentation.fragment.locations.LocationsFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DatabaseDataModule::class,
        ServiceDataModule::class,
        PresentationModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharactersFilterFragment)
    fun inject(fragment: CharacterDetailsFragment)

    fun inject(fragment: LocationsFragment)
    fun inject(fragment: LocationsFilterFragment)
    fun inject(fragment: LocationDetailsFragment)

    fun inject(fragment: EpisodesFragment)
    fun inject(fragment: EpisodesFilterFragment)
    fun inject(fragment: EpisodeDetailsFragment)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }

}