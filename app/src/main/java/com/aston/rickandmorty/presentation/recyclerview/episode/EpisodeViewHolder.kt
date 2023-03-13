package com.aston.rickandmorty.presentation.recyclerview.episode

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aston.rickandmorty.databinding.EpisodeItemBinding
import com.aston.rickandmorty.presentation.model.episode.EpisodeView

class EpisodeViewHolder(
    private val binding: EpisodeItemBinding,
) : ViewHolder(binding.root) {

    fun populate(episode: EpisodeView) {
        with(binding) {
            episodeName.text = episode.name
            episodeNumber.text = episode.episode
            episodeAirDate.text = episode.air_date
        }
    }

}