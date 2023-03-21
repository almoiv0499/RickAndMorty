package com.aston.rickandmorty.presentation.fragment.locations

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentLocationsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.recyclerview.location.LocationAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        setupRecyclerView()
    }

    override fun setupObservers() {
        super.setupObservers()

        observeLocationFlow()
    }

    override fun setToolbarTitle(): Int = R.string.locations_screen_name

    private fun setupRecyclerView() {
        with(binding.locationsRecyclerView) {
            adapter = locationAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    private fun observeLocationFlow() {
        val locationName = fetchFilteredData(LOCATION_NAME)
        val locationType = fetchFilteredData(LOCATION_TYPE)
        val locationDimension = fetchFilteredData(LOCATION_DIMENSION)

        lifecycleScope.launch {
            viewModel.locationsFlow(locationName, locationType, locationDimension)
                .collectLatest { paging ->
                    locationAdapter.submitData(viewLifecycleOwner.lifecycle, paging)
                }
        }
    }

    private fun fetchFilteredData(key: String): String {
        return arguments?.getString(key) ?: EMPTY_VALUE
    }
}