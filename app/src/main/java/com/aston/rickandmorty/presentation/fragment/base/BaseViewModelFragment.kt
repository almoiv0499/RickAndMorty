package com.aston.rickandmorty.presentation.fragment.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.viewmodel_factory.ViewModelFactory
import com.aston.rickandmorty.presentation.util.NavigationManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        observeExceptionMessage()
    }

    private fun observeNavigation() {
        viewModel.manageFragmentLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { navigator ->
                handleNavigation(navigator)
            }
        }
    }

    private fun observeExceptionMessage() {
        viewModel.exceptionLiveData.observe(viewLifecycleOwner) { exceptionMessage ->
            Toast.makeText(context, exceptionMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleNavigation(navigationManager: NavigationManager) {
        when (navigationManager) {
            is NavigationManager.LaunchFragment -> launchFragment(navigationManager.fragment)
            is NavigationManager.LaunchDialogFragment -> launchDialogFragment(
                navigationManager.fragment, navigationManager.fragmentTag
            )
            is NavigationManager.RefreshFragment -> refreshFragment(navigationManager.fragment)
        }
    }

    private fun launchFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    private fun launchDialogFragment(
        fragment: BottomSheetDialogFragment,
        fragmentTag: String,
    ) {
        val fragmentManager = activity?.supportFragmentManager!!
        fragment.show(fragmentManager, fragmentTag)
    }

    private fun refreshFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.popBackStack()
        activity?.supportFragmentManager?.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    protected fun <T : Any, VH : ViewHolder> observeLoadState(
        adapter: PagingDataAdapter<T, VH>,
        recyclerView: RecyclerView,
        filter: FloatingActionButton,
        progress: ProgressBar,
        errorMessage: TextView,
    ) {
        viewModel.internetConnectionLiveData.observe(viewLifecycleOwner) { hasInternetConnection ->
            lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest { loadState ->
                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0

                    recyclerView.isVisible = !isListEmpty
                    filter.isVisible = !isListEmpty
                    progress.isVisible = loadState.source.refresh == LoadState.Loading

                    errorMessage.isVisible = if (hasInternetConnection) {
                        loadState.source.refresh is LoadState.Error
                    } else {
                        isListEmpty
                    }
                }
            }
        }
    }

    protected fun getVisibilityByInternetConnection(hasInternetConnection: Boolean): Int {
        return if (hasInternetConnection) View.GONE
        else View.VISIBLE
    }

}