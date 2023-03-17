package com.aston.rickandmorty.presentation.activity.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aston.rickandmorty.presentation.activity.main.MainActivity

interface RouterMainActivity {

    fun onCreate(
        mainActivity: MainActivity,
        fragmentListener: FragmentManager.FragmentLifecycleCallbacks,
    )

    fun onDestroy(fragmentListener: FragmentManager.FragmentLifecycleCallbacks)

    fun navigateToCharactersFragment(fragment: Fragment)

    fun navigateBack()

}