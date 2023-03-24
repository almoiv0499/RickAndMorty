package com.aston.rickandmorty.presentation.activity.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.ActivityMainBinding
import com.aston.rickandmorty.presentation.activity.router.RouterMainActivity
import com.aston.rickandmorty.presentation.util.TitleToolbar
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var router: RouterMainActivity

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

        router.onCreate(this, fragmentListener)

        setSupportActionBar(binding.toolbarApp)

        setBottomNavigation()
        handleBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()

        router.onDestroy(fragmentListener)
    }

    private fun setBottomNavigation() {
        binding.bottomNavigationApp.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.characters -> {
                    router.launchCharactersFragment()
                }
                R.id.locations -> {
                    router.launchLocationsFragment()
                }
                R.id.episodes -> {
                    router.launchEpisodesFragment()
                }
            }
            true
        }
    }

    private fun updateUI() {
        val fragment = currentFragment
        setupToolbar()
        setupBackPressed(fragment)
        setBottomNavigationVisibility(fragment)
    }

    private fun setBottomNavigationVisibility(fragment: Fragment) {
        when (fragment) {
            is TitleToolbarDetails -> binding.bottomNavigationApp.visibility = View.GONE
            else -> binding.bottomNavigationApp.visibility = View.VISIBLE
        }
    }

    private fun setupToolbar() {
        when (val fragment = currentFragment) {
            is TitleToolbar -> binding.toolbarApp.title = getString(fragment.setToolbarTitle())
            is TitleToolbarDetails -> {
                binding.toolbarApp.title = fragment.setToolbarTitle()
            }
            else -> binding.toolbarApp.title = getString(R.string.app_name)
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

    private fun handleBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    isCharactersFragment() -> finish()
                    isTitleToolbarDetails() -> router.navigateBack()
                    else -> binding.bottomNavigationApp.selectedItemId = R.id.characters
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun isCharactersFragment(): Boolean =
        binding.bottomNavigationApp.selectedItemId == R.id.characters

    private fun isTitleToolbarDetails(): Boolean = currentFragment is TitleToolbarDetails

}