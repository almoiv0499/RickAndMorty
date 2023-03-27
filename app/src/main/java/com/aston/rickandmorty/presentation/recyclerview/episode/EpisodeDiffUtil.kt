package com.aston.rickandmorty.presentation.recyclerview.episode

import androidx.recyclerview.widget.DiffUtil
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoViewModel

class EpisodeDiffUtil : DiffUtil.ItemCallback<EpisodeInfoViewModel>() {

    override fun areItemsTheSame(oldItem: EpisodeInfoViewModel, newItem: EpisodeInfoViewModel): Boolean =
        oldItem.episodeId == newItem.episodeId

    override fun areContentsTheSame(oldItem: EpisodeInfoViewModel, newItem: EpisodeInfoViewModel): Boolean =
        oldItem == newItem

}