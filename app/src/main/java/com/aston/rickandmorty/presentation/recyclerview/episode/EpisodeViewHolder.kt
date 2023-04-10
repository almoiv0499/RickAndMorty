package com.aston.rickandmorty.presentation.recyclerview.episode

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aston.rickandmorty.databinding.EpisodeItemBinding
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoViewModel

class EpisodeViewHolder(
    private val binding: EpisodeItemBinding,
) : ViewHolder(binding.root) {

    fun populate(episode: EpisodeInfoViewModel, onEpisodeClickListener: OnEpisodeClickListener?) {
        with(binding) {
            episodeName.text = episode.episodeName
            episodeNumber.text = episode.episodeNumber
            episodeAirDate.text = episode.airDate

            episodeCardView.setOnClickListener {
                onEpisodeClickListener?.invoke(episode)
            }
        }
    }

}