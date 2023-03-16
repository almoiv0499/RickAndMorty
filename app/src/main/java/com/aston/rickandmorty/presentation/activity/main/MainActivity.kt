package com.aston.rickandmorty.presentation.activity.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.ActivityMainBinding
import com.aston.rickandmorty.presentation.activity.router.RouterMainActivity
import com.aston.rickandmorty.presentation.fragment.characters.CharactersFragment
import com.aston.rickandmorty.presentation.util.TitleToolbar
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var optionsMenu: Menu? = null

    @Inject lateinit var router: RouterMainActivity

    private val component by lazy(LazyThreadSafetyMode.NONE) {
        (applicationContext as App).component
    }

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
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarApp)

        register()
        setBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregister()
    }

    private fun setBottomNavigation() {
        binding.bottomNavigationApp.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.characters -> {
                    router.navigateToCharactersFragment(CharactersFragment.newInstance())
                }
                R.id.locations -> {

                }
                R.id.episodes -> {

                }
            }
            true
        }
    }

    private fun register() {
        router.onCreate(this)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    private fun unregister() {
        router.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    private fun updateUI() {
        val fragment = currentFragment
        hideToolbarItem(optionsMenu)
        setupToolbar()
        setupBackPressed(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        optionsMenu = menu
        return true
    }

    private fun setupToolbar() {
        when (val fragment = currentFragment) {
            is TitleToolbar -> binding.toolbarApp.title = getString(fragment.setToolbarTitle())
            is TitleToolbarDetails -> binding.toolbarApp.title = fragment.setToolbarTitle()
            else -> binding.toolbarApp.title = getString(R.string.app_name)
        }
    }

    private fun hideToolbarItem(menu: Menu?) {
        val search = menu?.findItem(R.id.search)
        when {
            isTitleToolbarDetails() -> {
                search?.isVisible = false
            }
            else -> {
                search?.isVisible = true
            }
        }
    }

    private fun setupBackPressed(fragment: Fragment) {
        when (fragment) {
            is TitleToolbarDetails -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                binding.toolbarApp.setNavigationOnClickListener {
                    router.navigateBack()
                }

            }
            else -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
            }
        }
    }

    private fun isTitleToolbarDetails() = currentFragment is TitleToolbarDetails

}