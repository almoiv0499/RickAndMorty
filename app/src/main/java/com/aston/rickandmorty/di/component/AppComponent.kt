package com.aston.rickandmorty.di.component

import android.content.Context
import com.aston.rickandmorty.di.annotation.AppScope
import com.aston.rickandmorty.di.module.DataModule
import com.aston.rickandmorty.di.module.DomainModule
import com.aston.rickandmorty.di.module.PresentationModule
import com.aston.rickandmorty.di.module.ViewModelModule
import com.aston.rickandmorty.presentation.activity.main.MainActivity
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.fragment.characters.CharactersFragment
import com.aston.rickandmorty.presentation.fragment.characters_filter.CharactersFilterFragment
import com.aston.rickandmorty.presentation.fragment.locations.LocationsFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        PresentationModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharactersFilterFragment)
    fun inject(fragment: CharacterDetailsFragment)

    fun inject(fragment: LocationsFragment)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

}