package com.aston.rickandmorty.presentation.fragment.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.util.Navigator

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.navigationToLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { navigator ->
                handleNavigation(navigator)
            }
        }
    }

    private fun handleNavigation(navigator: Navigator) {
        when (navigator) {
            is Navigator.NavigateTo -> navigateTo(navigator.fragment)
            is Navigator.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun navigateTo(fragment: Fragment) {
        activity?.let { activity ->
            activity.supportFragmentManager.commit {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
            }
        }
    }

}