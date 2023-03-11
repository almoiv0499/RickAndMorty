package com.aston.rickandmorty.presentation.activity.main

import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.presentation.activity.router.RouterMainActivity
import com.aston.rickandmorty.presentation.fragment.characters.CharactersFragment
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: RouterMainActivity
) : ViewModel() {

    fun onCreateRouter(activity: MainActivity) {
        router.onCreate(activity)
    }

    fun onDestroyRouter() {
        router.onDestroy()
    }

    fun navigateToCharactersFragment() {
        router.navigateToCharactersFragment(CharactersFragment.newInstance())
    }

    fun navigateBack() {
        router.navigateBack()
    }

}