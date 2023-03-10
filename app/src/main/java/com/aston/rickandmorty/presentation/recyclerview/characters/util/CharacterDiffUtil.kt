package com.aston.rickandmorty.presentation.recyclerview.characters.util

import androidx.recyclerview.widget.DiffUtil
import com.aston.rickandmorty.presentation.model.CharacterInfoView

class CharacterDiffUtil : DiffUtil.ItemCallback<CharacterInfoView>() {

    override fun areItemsTheSame(oldItem: CharacterInfoView, newItem: CharacterInfoView): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CharacterInfoView,
        newItem: CharacterInfoView,
    ): Boolean = oldItem == newItem

}