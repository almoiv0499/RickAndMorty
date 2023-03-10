package com.aston.rickandmorty.presentation.recyclerview.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.aston.rickandmorty.databinding.CharacterItemBinding
import com.aston.rickandmorty.presentation.model.CharacterInfoView
import com.aston.rickandmorty.presentation.recyclerview.characters.util.CharacterDiffUtil

typealias OnCharacterClickListener = (CharacterInfoView) -> Unit

class CharacterAdapter : ListAdapter<CharacterInfoView, CharacterViewHolder>(CharacterDiffUtil()) {

    var onCharacterClickListener: OnCharacterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = currentList[position]
        holder.populate(character, onCharacterClickListener)
    }

}