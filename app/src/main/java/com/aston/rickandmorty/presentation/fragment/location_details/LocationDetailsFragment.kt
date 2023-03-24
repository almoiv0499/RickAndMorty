package com.aston.rickandmorty.presentation.fragment.location_details

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.model.location.LocationInfoView
import com.aston.rickandmorty.presentation.recyclerview.characters.CharactersInLocationAdapter
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
        fun newInstance(location: LocationInfoView): LocationDetailsFragment {
            val fragment = LocationDetailsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(LOCATION_ARGS_KEY, location)
            }
            return fragment
        }
    }

    private val characterAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharactersInLocationAdapter().apply {
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

        observeCharacterList()
    }

    override fun setUI() {
        setupArguments()
    }

    override fun setToolbarTitle(): String {
        val location = getLocationArguments()
        return location?.name ?: getString(R.string.locations_screen_name)
    }

    private fun observeCharacterList() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { characters ->
            binding.locationDetailsProgressBar.visibility = View.GONE
            characterAdapter.submitList(characters)
        }
    }

    override fun setupRecyclerView() {
        with(binding.charactersInLocationRecyclerView) {
            adapter = characterAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    private fun setupArguments() {
        val location = getLocationArguments()
        if (location != null) {
            viewModel.fetchCharactersLiveData(location.residents)

            with(binding) {
                locationDetailsName.text = location.name
                locationDetailsType.text = location.type
                locationDetailsDimension.text = location.dimension
            }
        }
    }

    private fun getLocationArguments(): LocationInfoView? {
        return arguments?.parcelable(LOCATION_ARGS_KEY)
    }
}