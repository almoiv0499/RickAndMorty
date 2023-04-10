package com.aston.rickandmorty.presentation.fragment.character_details

import android.os.Bundle
import android.view.View
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.model.character.CharacterInfoViewModel
import com.aston.rickandmorty.presentation.recyclerview.episode.InnerEpisodeAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails
import com.aston.rickandmorty.presentation.util.parcelable
import com.bumptech.glide.Glide

private const val CHARACTER_ARGS_KEY = "character_args_key"

class CharacterDetailsFragment :
    BaseViewModelFragment<FragmentCharacterDetailsBinding, CharacterDetailsViewModel>(
        R.layout.fragment_character_details,
        FragmentCharacterDetailsBinding::inflate,
        CharacterDetailsViewModel::class.java
    ), TitleToolbarDetails {

    companion object {
        fun newInstance(character: CharacterInfoViewModel): CharacterDetailsFragment {
            val fragment = CharacterDetailsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(CHARACTER_ARGS_KEY, character)
            }
            return fragment
        }
    }

    private val character by lazy(LazyThreadSafetyMode.NONE) {
        getCharacterArguments()
    }

    private val episodeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        InnerEpisodeAdapter().apply {
            onEpisodeClickListener = { episode ->
                viewModel.launchEpisodeDetailsFragment(episode)
            }
        }
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        setupArguments()
        passArgumentsToViewModel()
        swipeToRefresh()
    }

    override fun setupObservers() {
        super.setupObservers()

        observeEpisodes()
        observeLocation()
        observeOriginLocation()
        observeInternetConnection()
    }

    override fun setupRecyclerView() {
        with(binding.characterEpisodeRecyclerView) {
            adapter = episodeAdapter
        }
    }

    override fun setToolbarTitle(): String = getString(R.string.characterDetails_screen_name)

    private fun observeEpisodes() {
        viewModel.episodesLiveData.observe(viewLifecycleOwner) { episodes ->
            binding.charactersDetailsProgressBar.visibility = View.GONE
            episodeAdapter.submitList(episodes)
        }
    }

    private fun observeLocation() {
        viewModel.locationLiveData.observe(viewLifecycleOwner) { location ->
            binding.locationForCharacterDetailsName.text = location.name

            binding.locationForCharacterDetailsName.setOnClickListener {
                viewModel.launchLocationDetailsFragment(location)
            }
        }
    }

    private fun observeOriginLocation() {
        viewModel.originLocationLiveData.observe(viewLifecycleOwner) { location ->
            binding.originForCharacterDetailsName.text = location.name

            binding.originForCharacterDetailsName.setOnClickListener {
                viewModel.launchLocationDetailsFragment(location)
            }
        }
    }

    private fun observeInternetConnection() {
        viewModel.internetConnectionLiveData.observe(viewLifecycleOwner) { hasInternetConnection ->
            binding.internetConnectionMessage.visibility =
                getVisibilityByInternetConnection(hasInternetConnection)
        }
    }

    private fun swipeToRefresh() {
        character?.let {
            binding.characterDetailsSwipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshFragment(it)
                binding.characterDetailsSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupArguments() {
        character?.let { characterNotNull ->
            with(binding) {
                characterDetailsName.text = characterNotNull.name
                characterDetailsStatus.text = characterNotNull.status
                characterDetailsGender.text = characterNotNull.gender
                characterDetailsSpecies.text = characterNotNull.species

                Glide.with(this@CharacterDetailsFragment).load(characterNotNull.image)
                    .placeholder(R.drawable.ic_launcher_foreground).into(characterDetailsImage)
            }
        }
    }

    private fun passArgumentsToViewModel() {
        character?.let { characterNotNull ->
            viewModel.getEpisodes(characterNotNull.episodes)
            viewModel.getLocationById(characterNotNull.location.locationInfo)
            viewModel.getOriginLocationById(characterNotNull.origin.originLocationName)
        }
    }

    private fun getCharacterArguments(): CharacterInfoViewModel? {
        return arguments?.parcelable(CHARACTER_ARGS_KEY)
    }
}