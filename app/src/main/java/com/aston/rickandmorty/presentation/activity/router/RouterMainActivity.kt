package com.aston.rickandmorty.presentation.activity.router

import androidx.fragment.app.FragmentManager
import com.aston.rickandmorty.presentation.activity.main.MainActivity

interface RouterMainActivity {

    fun onCreate(
        mainActivity: MainActivity,
        fragmentListener: FragmentManager.FragmentLifecycleCallbacks,
    )

    fun onDestroy(fragmentListener: FragmentManager.FragmentLifecycleCallbacks)

    fun launchCharactersFragment()

    fun launchLocationsFragment()

    fun navigateBack()

}