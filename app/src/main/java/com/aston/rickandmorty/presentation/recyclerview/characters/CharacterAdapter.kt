package com.aston.rickandmorty.presentation.recyclerview.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.aston.rickandmorty.databinding.CharacterItemBinding
import com.aston.rickandmorty.presentation.model.character.CharacterView
import com.aston.rickandmorty.presentation.recyclerview.characters.util.CharacterDiffUtil

typealias OnCharacterClickListener = (CharacterView) -> Unit

class CharacterAdapter :
    PagingDataAdapter<CharacterView, CharacterViewHolder>(CharacterDiffUtil()) {

    var onCharacterClickListener: OnCharacterClickListener? = null

    // Доработаю позже
//    private var filteredCharacters = mutableListOf<CharacterView>()
//
//    fun setFilteredCharacters(filtered: List<CharacterView>) {
//        submitList(filtered)
//        filteredCharacters = ArrayList(filtered)
//    }

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

    // Доработаю позже
//    override fun getFilter(): Filter = FilterClass.filter(filteredCharacters, this)
}