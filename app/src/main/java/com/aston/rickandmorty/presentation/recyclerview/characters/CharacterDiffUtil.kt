package com.aston.rickandmorty.presentation.recyclerview.characters

import androidx.recyclerview.widget.DiffUtil
import com.aston.rickandmorty.presentation.model.character.CharacterInfoViewModel

class CharacterDiffUtil : DiffUtil.ItemCallback<CharacterInfoViewModel>() {

    override fun areItemsTheSame(oldItem: CharacterInfoViewModel, newItem: CharacterInfoViewModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CharacterInfoViewModel,
        newItem: CharacterInfoViewModel,
    ): Boolean = oldItem == newItem

}