package com.aston.rickandmorty.presentation.activity.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.activity.main.MainActivity

object RouterMainActivityImpl : RouterMainActivity {

    private var activity: MainActivity? = null

    override fun onCreate(
        mainActivity: MainActivity,
        fragmentListener: FragmentManager.FragmentLifecycleCallbacks,
    ) {
        activity = mainActivity
        activity?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
            fragmentListener, false
        )
    }

    override fun navigateToCharactersFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.popBackStack(
            null, FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        activity?.supportFragmentManager?.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    override fun navigateBack() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onDestroy(fragmentListener: FragmentManager.FragmentLifecycleCallbacks) {
        activity?.supportFragmentManager?.unregisterFragmentLifecycleCallbacks(fragmentListener)
        activity = null
    }

}