package com.aston.rickandmorty.presentation.fragment.character_details

import android.os.Bundle
import android.util.Log
import android.view.View
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import com.aston.rickandmorty.presentation.recyclerview.episode.EpisodesInCharacterAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails
import com.aston.rickandmorty.presentation.util.parcelable
import com.bumptech.glide.Glide

private const val CHARACTER_ARGS_KEY = "character_args_key"

private fun log(message: String) {
    Log.d("ADAPTER_TAG", message)
}

class CharacterDetailsFragment :
    BaseViewModelFragment<FragmentCharacterDetailsBinding, CharacterDetailsViewModel>(
        R.layout.fragment_character_details,
        FragmentCharacterDetailsBinding::inflate,
        CharacterDetailsViewModel::class.java
    ), TitleToolbarDetails {

    companion object {
        fun newInstance(character: CharacterInfoView): CharacterDetailsFragment {
            val fragment = CharacterDetailsFragment()
            val args = Bundle().apply {
                putParcelable(CHARACTER_ARGS_KEY, character)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val episodeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        EpisodesInCharacterAdapter().apply {
            onEpisodeClickListener = { episode ->
                viewModel.launchEpisodeDetailsFragment(episode)
            }
        }
    }

    private fun getCharacterArguments(): CharacterInfoView? {
        return arguments?.parcelable(CHARACTER_ARGS_KEY)
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        setupArguments()
        setArgumentsForViewModels()
    }

    override fun setupObservers() {
        super.setupObservers()

        observeEpisodes()
        observeLocation()
        observeOriginLocation()
    }

    private fun observeEpisodes() {
        viewModel.episodesLiveData.observe(viewLifecycleOwner) { episodes ->
            binding.charactersDetailsProgressBar.visibility = View.GONE
            episodeAdapter.submitList(episodes)
        }
    }

    private fun observeLocation() {
        viewModel.locationLiveData.observe(viewLifecycleOwner) { location ->
            binding.locationForCharacterDetailsName.text = location.name
            binding.locationForCharacterDetailsType.text = location.type
            binding.locationForCharacterDetailsDimension.text = location.dimension

            binding.locationForCharacterDetailsCardView.setOnClickListener {
                viewModel.launchLocationDetailsFragment(location)
            }
        }
    }

    private fun observeOriginLocation() {
        viewModel.originLocationLiveData.observe(viewLifecycleOwner) { location ->
            binding.originForCharacterDetailsName.text = location.name
            binding.originForCharacterDetailsType.text = location.type
            binding.originForCharacterDetailsDimension.text = location.dimension

            binding.originForCharacterDetailsCardView.setOnClickListener {
                viewModel.launchLocationDetailsFragment(location)
            }
        }
    }

    override fun setupRecyclerView() {
        with(binding.characterEpisodeRecyclerView) {
            adapter = episodeAdapter
        }
    }

    private fun setupArguments() {
        val character = getCharacterArguments()
        if (character != null) {

            with(binding) {
                characterDetailsName.text = character.name
                characterDetailsStatus.text = character.status
                characterDetailsGender.text = character.gender
                characterDetailsSpecies.text = character.species

                Glide.with(this@CharacterDetailsFragment).load(character.image)
                    .placeholder(R.drawable.ic_launcher_foreground).into(characterDetailsImage)
            }
        }
    }

    private fun setArgumentsForViewModels() {
        val character = getCharacterArguments()
        if (character != null) {
            viewModel.fetchEpisodeLiveData(character.episodes)
            viewModel.fetchLocationById(character.location.locationInfo)
            viewModel.fetchOriginLocationById(character.origin.originLocationName)
        }
    }

    override fun setToolbarTitle(): String {
        val character = getCharacterArguments()
        return character?.name ?: getString(R.string.characters_screen_name)
    }
}