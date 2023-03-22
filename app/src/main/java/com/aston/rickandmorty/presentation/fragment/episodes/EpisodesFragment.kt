package com.aston.rickandmorty.presentation.fragment.episodes

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentEpisodesBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.recyclerview.episode.EpisodeAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar

private const val SPAN_COUNT = 2
private const val EPISODE_NAME = "episode name"
private const val EPISODE_NUMBER = "episode number"
private const val EMPTY_VALUE = ""

class EpisodesFragment : BaseViewModelFragment<FragmentEpisodesBinding, EpisodesViewModel>(
    R.layout.fragment_episodes, FragmentEpisodesBinding::inflate, EpisodesViewModel::class.java
), TitleToolbar {

    companion object {
        fun newInstance() = EpisodesFragment()

        fun newInstance(episodeName: String, episodeNumber: String): EpisodesFragment {
            val fragment = EpisodesFragment()
            fragment.arguments = Bundle().apply {
                putString(EPISODE_NAME, episodeName)
                putString(EPISODE_NUMBER, episodeNumber)
            }
            return fragment
        }
    }

    private val episodeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        EpisodeAdapter().apply {
            onEpisodeClickListener = { episode ->
                viewModel.launchEpisodeDetailsFragment(episode)
            }
        }
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        observeEpisode()
    }

    override fun setUI() {
        setupRecyclerView()
        launchFilterFragment()
    }

    override fun setToolbarTitle(): Int = R.string.episodes_screen_name

    private fun launchFilterFragment() {
        binding.episodeFilter.setOnClickListener {
            viewModel.launchFilterFragment()
        }
    }

    private fun observeEpisode() {
        val episodeName = fetchFilteredData(EPISODE_NAME)
        val episodeNumber = fetchFilteredData(EPISODE_NUMBER)
        viewModel.fetch(episodeName, episodeNumber)

        viewModel.episodesLD.observe(viewLifecycleOwner) { paging ->
            episodeAdapter.submitData(viewLifecycleOwner.lifecycle, paging)
        }
    }

    private fun setupRecyclerView() {
        with(binding.episodesRecyclerView) {
            adapter = episodeAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    private fun fetchFilteredData(key: String): String {
        return arguments?.getString(key) ?: EMPTY_VALUE
    }
}