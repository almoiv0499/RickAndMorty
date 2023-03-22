package com.aston.rickandmorty.presentation.fragment.episode_details

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView
import com.aston.rickandmorty.presentation.recyclerview.characters.CharactersInLocationAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails
import com.aston.rickandmorty.presentation.util.parcelable

private const val SPAN_COUNT = 2
private const val EPISODE_ARGS_KEY = "episode_args_key"

class EpisodeDetailsFragment :
    BaseViewModelFragment<FragmentEpisodeDetailsBinding, EpisodeDetailsViewModel>(
        R.layout.fragment_episode_details,
        FragmentEpisodeDetailsBinding::inflate,
        EpisodeDetailsViewModel::class.java
    ), TitleToolbarDetails {

    companion object {
        fun newInstance(episode: EpisodeInfoView): EpisodeDetailsFragment {
            val fragment = EpisodeDetailsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(EPISODE_ARGS_KEY, episode)
            }
            return fragment
        }
    }

    private val innerCharacterAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharactersInLocationAdapter().apply {
            onCharacterClickListener = {character ->
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
    }

    override fun setUI() {
        setupRecyclerView()

        setupArguments()
    }

    override fun setToolbarTitle(): String {
        val episode = getEpisodeArguments()
        return episode?.episodeName ?: getString(R.string.episodes_screen_name)
    }

    private fun observeCharacters() {
        val episode = getEpisodeArguments()
        if (episode != null) {
            viewModel.fetchCharactersLiveData(episode.characters)
            viewModel.charactersLiveData.observe(viewLifecycleOwner) { characters ->
                innerCharacterAdapter.submitList(characters)
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.charactersInEpisodeRecyclerView) {
            adapter = innerCharacterAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    private fun setupArguments() {
        val episode = getEpisodeArguments()
        if (episode != null) {
            with(binding) {
                episodeDetailName.text = episode.episodeName
                episodeDetailNumber.text = episode.episodeNumber
                episodeDetailAirDate.text = episode.airDate
            }
        }
    }

    private fun getEpisodeArguments(): EpisodeInfoView? {
        return arguments?.parcelable(EPISODE_ARGS_KEY)
    }
}