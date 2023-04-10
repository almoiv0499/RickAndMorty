package com.aston.rickandmorty.presentation.fragment.episode_details

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoViewModel
import com.aston.rickandmorty.presentation.recyclerview.characters.InnerCharactersAdapter
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
        fun newInstance(episode: EpisodeInfoViewModel): EpisodeDetailsFragment {
            val fragment = EpisodeDetailsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(EPISODE_ARGS_KEY, episode)
            }
            return fragment
        }
    }

    private val episode by lazy(LazyThreadSafetyMode.NONE) {
        getEpisodeArguments()
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
        with(binding.charactersInEpisodeRecyclerView) {
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = characterAdapter
        }
    }

    override fun setToolbarTitle(): String = getString(R.string.episodeDetails_screen_name)

    private fun observeCharacters() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { characters ->
            binding.episodeDetailsProgressBar.visibility = View.GONE
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
        episode?.let { episodeNotNull ->
            binding.episodeDetailsSwipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshEpisodeDetailsFragment(episodeNotNull)
                binding.episodeDetailsSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupArguments() {
        episode?.let { episodeNotNull ->
            viewModel.getCharacters(episodeNotNull.characters)

            with(binding) {
                episodeDetailName.text = episodeNotNull.episodeName
                episodeDetailNumber.text = episodeNotNull.episodeNumber
                episodeDetailAirDate.text = episodeNotNull.airDate
            }
        }
    }

    private fun getEpisodeArguments(): EpisodeInfoViewModel? {
        return arguments?.parcelable(EPISODE_ARGS_KEY)
    }
}