package com.aston.rickandmorty.presentation.recyclerview.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.aston.rickandmorty.databinding.CharacterItemBinding
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView

typealias OnCharacterClickListener = (CharacterInfoView) -> Unit

class CharacterAdapter :
    PagingDataAdapter<CharacterInfoView, CharacterViewHolder>(CharacterDiffUtil()) {

    var onCharacterClickListener: OnCharacterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        if (character != null) {
            holder.populate(character, onCharacterClickListener)
        }
    }
}