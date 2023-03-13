package com.aston.rickandmorty.presentation.recyclerview.episode

import androidx.recyclerview.widget.DiffUtil
import com.aston.rickandmorty.presentation.model.episode.EpisodeView

class EpisodeDiffUtil : DiffUtil.ItemCallback<EpisodeView>() {

    override fun areItemsTheSame(oldItem: EpisodeView, newItem: EpisodeView): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: EpisodeView, newItem: EpisodeView): Boolean =
        oldItem == newItem

}