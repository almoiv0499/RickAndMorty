package com.aston.rickandmorty.presentation.fragment.episodes

import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentEpisodesBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.recyclerview.episode.EpisodeAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar

private const val SPAN_COUNT = 2

class EpisodesFragment : BaseViewModelFragment<FragmentEpisodesBinding, EpisodesViewModel>(
    R.layout.fragment_episodes, FragmentEpisodesBinding::inflate, EpisodesViewModel::class.java
), TitleToolbar {

    companion object {
        fun newInstance() = EpisodesFragment()
    }

    private val episodeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        EpisodeAdapter()
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
    }

    override fun setToolbarTitle(): Int = R.string.episodes_screen_name

    private fun observeEpisode() {

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
}