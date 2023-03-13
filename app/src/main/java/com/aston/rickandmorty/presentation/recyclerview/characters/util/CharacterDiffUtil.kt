package com.aston.rickandmorty.presentation.recyclerview.characters.util

import androidx.recyclerview.widget.DiffUtil
import com.aston.rickandmorty.presentation.model.character.CharacterView

class CharacterDiffUtil : DiffUtil.ItemCallback<CharacterView>() {

    override fun areItemsTheSame(oldItem: CharacterView, newItem: CharacterView): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CharacterView,
        newItem: CharacterView,
    ): Boolean = oldItem == newItem

}