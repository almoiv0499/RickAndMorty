package com.aston.rickandmorty.presentation.activity.router

import androidx.fragment.app.FragmentManager
import com.aston.rickandmorty.presentation.activity.main.MainActivity

interface MainActivityRouter {

    fun onCreate(
        mainActivity: MainActivity,
        fragmentListener: FragmentManager.FragmentLifecycleCallbacks,
    )

    fun onDestroy(fragmentListener: FragmentManager.FragmentLifecycleCallbacks)

    fun launchCharactersFragment()

    fun launchLocationsFragment()

    fun launchEpisodesFragment()

    fun navigateBack()

}