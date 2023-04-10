package com.aston.rickandmorty.presentation.recyclerview.loader_state

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aston.rickandmorty.databinding.LoadStateFooterBinding

class LoadStateViewHolder(
    private val binding: LoadStateFooterBinding,
) : ViewHolder(binding.root) {

    fun populate(loadState: LoadState) {
        binding.progressLoadState.isVisible = loadState is LoadState.Loading
    }

}