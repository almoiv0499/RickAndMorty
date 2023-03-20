package com.aston.rickandmorty.presentation.fragment.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.viewmodel_factory.ViewModelFactory
import com.aston.rickandmorty.presentation.util.Navigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

abstract class BaseViewModelFragment<VB : ViewBinding, VM : BaseViewModel>(
    @LayoutRes layoutRes: Int,
    bindingInflater: (inflater: LayoutInflater) -> VB,
    private val viewModelClass: Class<VM>,
) : BaseFragment<VB>(layoutRes, bindingInflater) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    protected lateinit var viewModel: VM

    override fun onAttach(context: Context) {
        super.onAttach(context)

        injectDependencies()
        viewModel = ViewModelProvider(
            viewModelStore, viewModelFactory
        )[viewModelClass]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    abstract fun injectDependencies()

    protected open fun setupObservers() {
        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.launchFragmentLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { navigator ->
                handleNavigation(navigator)
            }
        }
    }

    private fun handleNavigation(navigator: Navigator) {
        when (navigator) {
            is Navigator.LaunchFragment -> launchFragment(navigator.fragment)
            is Navigator.LaunchDialogFragment -> launchDialogFragment(
                navigator.fragment, navigator.fragmentTag
            )
            is Navigator.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun launchFragment(fragment: Fragment) {
        activity?.let { activity ->
            activity.supportFragmentManager.commit {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
            }
        }
    }

    private fun launchDialogFragment(
        fragment: BottomSheetDialogFragment,
        fragmentTag: String,
    ) {
        val fragmentManager = activity?.supportFragmentManager!!
        fragment.show(fragmentManager, fragmentTag)
    }

}