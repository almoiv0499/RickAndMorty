package com.aston.rickandmorty.presentation.fragment.locations

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentLocationsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.recyclerview.loader_state.LoaderStateFooterAdapter
import com.aston.rickandmorty.presentation.recyclerview.location.LocationAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar

private const val EMPTY_VALUE = ""
private const val SPAN_COUNT = 2
private const val LOCATION_NAME = "location name"
private const val LOCATION_TYPE = "location type"
private const val LOCATION_DIMENSION = "location dimension"

class LocationsFragment : BaseViewModelFragment<FragmentLocationsBinding, LocationsViewModel>(
    R.layout.fragment_locations, FragmentLocationsBinding::inflate, LocationsViewModel::class.java
), TitleToolbar {

    companion object {
        fun newInstance() = LocationsFragment()

        fun newInstance(
            locationName: String,
            locationType: String,
            locationDimension: String,
        ): LocationsFragment {
            val fragment = LocationsFragment()
            fragment.arguments = Bundle().apply {
                putString(LOCATION_NAME, locationName)
                putString(LOCATION_TYPE, locationType)
                putString(LOCATION_DIMENSION, locationDimension)
            }
            return fragment
        }
    }

    private val locationAdapter by lazy(LazyThreadSafetyMode.NONE) {
        LocationAdapter().apply {
            onLocationClickListener = { location ->
                viewModel.launchLocationDetailsFragment(location)
            }
        }
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        setArguments()
        swipeToRefreshLayout()
        launchFilterFragment()
    }

    override fun setupObservers() {
        super.setupObservers()

        observeLocationFlow()
    }

    override fun setToolbarTitle(): Int = R.string.locations_screen_name

    override fun setupRecyclerView() {
        with(binding) {
            locationsRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
            locationsRecyclerView.adapter = locationAdapter.withLoadStateFooter(
                footer = LoaderStateFooterAdapter()
            )

            locationAdapter.addLoadStateListener { loadState ->
                checkLoadState(
                    loadState = loadState,
                    adapter = locationAdapter,
                    recyclerView = locationsRecyclerView,
                    progress = locationsProgressBar,
                    filter = locationFilter,
                    errorMessage = locationsErrorMessage
                )
            }
        }
    }

    private fun launchFilterFragment() {
        binding.locationFilter.setOnClickListener {
            viewModel.launchFilterFragment()
        }
    }

    private fun observeLocationFlow() {
        viewModel.locationsLiveData.observe(viewLifecycleOwner) { paging ->
            locationAdapter.submitData(viewLifecycleOwner.lifecycle, paging)
        }
    }

    private fun swipeToRefreshLayout() {
        binding.locationsSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshFragment()
            binding.locationsSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setArguments() {
        val locationName = fetchFilteredData(LOCATION_NAME)
        val locationType = fetchFilteredData(LOCATION_TYPE)
        val locationDimension = fetchFilteredData(LOCATION_DIMENSION)

        viewModel.locationsFlow(locationName, locationType, locationDimension)
    }

    private fun fetchFilteredData(key: String): String {
        return arguments?.getString(key) ?: EMPTY_VALUE
    }
}