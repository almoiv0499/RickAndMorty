package com.aston.rickandmorty.presentation.recyclerview.episode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.aston.rickandmorty.databinding.EpisodeItemBinding
import com.aston.rickandmorty.presentation.model.episode.EpisodeView

class EpisodeAdapter : ListAdapter<EpisodeView, EpisodeViewHolder>(EpisodeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = EpisodeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = currentList[position]
        holder.populate(episode)
    }

}