package com.aston.rickandmorty.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.databinding.ActivityMainBinding
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.util.TitleToolbar
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var optionsMenu: Menu? = null

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?,
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarApp)

        register()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregister()
    }

    private fun register() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    private fun unregister() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    private fun updateUI() {
        hideToolbarItem(optionsMenu)
        setupToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        optionsMenu = menu
        return true
    }

    private fun setupToolbar() {
        when(val fragment = currentFragment) {
            is TitleToolbar -> binding.toolbarApp.title = getString(fragment.setToolbarTitle())
            is TitleToolbarDetails -> binding.toolbarApp.title = fragment.setToolbarTitle()
            else -> binding.toolbarApp.title = getString(R.string.app_name)
        }
    }

    private fun hideToolbarItem(menu: Menu?) {
        val search = menu?.findItem(R.id.search)
        val filter = menu?.findItem(R.id.filter)

        when {
            isCharacterDetailsFragment() -> {
                search?.isVisible = false
                filter?.isVisible = false
            }
            else -> {
                search?.isVisible = true
                filter?.isVisible = true
            }
        }
    }

    private fun isCharacterDetailsFragment() = currentFragment is CharacterDetailsFragment

}