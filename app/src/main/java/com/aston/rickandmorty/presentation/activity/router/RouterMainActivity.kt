package com.aston.rickandmorty.presentation.activity.router

import androidx.fragment.app.Fragment
import com.aston.rickandmorty.presentation.activity.main.MainActivity

interface RouterMainActivity {

    fun onCreate(mainActivity: MainActivity)

    fun onDestroy()

    fun navigateToCharactersFragment(fragment: Fragment)

    fun navigateBack()

}