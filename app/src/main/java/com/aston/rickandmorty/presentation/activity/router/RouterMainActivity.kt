package com.aston.rickandmorty.presentation.activity.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.activity.main.MainActivity

object RouterMainActivity {

    private var activity: MainActivity? = null

    fun onCreate(mainActivity: MainActivity) {
        activity = mainActivity
    }

    fun navigateToCharactersFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.popBackStack(
            null, FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        activity?.supportFragmentManager?.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    fun navigateBack() {
        activity?.supportFragmentManager?.popBackStack()
    }

    fun onDestroy() {
        activity = null
    }

}