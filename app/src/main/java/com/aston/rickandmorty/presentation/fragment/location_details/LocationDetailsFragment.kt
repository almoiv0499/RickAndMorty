package com.aston.rickandmorty.presentation.fragment.location_details

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.model.location.LocationInfoViewModel
import com.aston.rickandmorty.presentation.recyclerview.characters.InnerCharactersAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails
import com.aston.rickandmorty.presentation.util.parcelable

private const val SPAN_COUNT = 2
private const val LOCATION_ARGS_KEY = "location_args_key"

class LocationDetailsFragment :
    BaseViewModelFragment<FragmentLocationDetailsBinding, LocationDetailsViewModel>(
        R.layout.fragment_location_details,
        FragmentLocationDetailsBinding::inflate,
        LocationDetailsViewModel::class.java
    ), TitleToolbarDetails {

    companion object {
        fun newInstance(location: LocationInfoViewModel): LocationDetailsFragment {
            val fragment = LocationDetailsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(LOCATION_ARGS_KEY, location)
            }
            return fragment
        }
    }

    private val location by lazy(LazyThreadSafetyMode.NONE) {
        getLocationArguments()
    }

    private val characterAdapter by lazy(LazyThreadSafetyMode.NONE) {
        InnerCharactersAdapter().apply {
            onCharacterClickListener = { character ->
                viewModel.launchCharacterDetailsFragment(character)
            }
        }
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        observeCharacters()
        observeInternetConnection()
    }

    override fun setUI() {
        setupArguments()
        swipeToRefresh()
    }

    override fun setupRecyclerView() {
        with(binding.charactersInLocationRecyclerView) {
            adapter = characterAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    override fun setToolbarTitle(): String = getString(R.string.locationDetails_screen_name)

    private fun observeCharacters() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { characters ->
            binding.locationDetailsProgressBar.visibility = View.GONE
            characterAdapter.submitList(characters)
        }
    }

    private fun observeInternetConnection() {
        viewModel.internetConnectionLiveData.observe(viewLifecycleOwner) { hasInternetConnection ->
            binding.internetConnectionMessage.visibility =
                getVisibilityByInternetConnection(hasInternetConnection)
        }
    }

    private fun swipeToRefresh() {
        location?.let {
            binding.locationDetailsSwipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshFragment(it)
                binding.locationDetailsSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupArguments() {
        location?.let { locationNotNull ->
            viewModel.getCharacters(locationNotNull.residents)

            with(binding) {
                locationDetailsName.text = locationNotNull.name
                locationDetailsType.text = locationNotNull.type
                locationDetailsDimension.text = locationNotNull.dimension
            }
        }
    }

    private fun getLocationArguments(): LocationInfoViewModel? {
        return arguments?.parcelable(LOCATION_ARGS_KEY)
    }
}