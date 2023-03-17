package com.aston.rickandmorty.presentation.recyclerview.episode

import androidx.recyclerview.widget.DiffUtil
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView

class EpisodeDiffUtil : DiffUtil.ItemCallback<EpisodeInfoView>() {

    override fun areItemsTheSame(oldItem: EpisodeInfoView, newItem: EpisodeInfoView): Boolean =
        oldItem.episodeId == newItem.episodeId

    override fun areContentsTheSame(oldItem: EpisodeInfoView, newItem: EpisodeInfoView): Boolean =
        oldItem == newItem

}