package com.aston.rickandmorty.presentation.recyclerview.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import com.aston.rickandmorty.databinding.CharacterItemBinding
import com.aston.rickandmorty.presentation.model.CharacterInfoView
import com.aston.rickandmorty.presentation.recyclerview.characters.util.CharacterDiffUtil
import com.aston.rickandmorty.presentation.util.FilterClass

typealias OnCharacterClickListener = (CharacterInfoView) -> Unit

class CharacterAdapter : ListAdapter<CharacterInfoView, CharacterViewHolder>(CharacterDiffUtil()),
    Filterable {

    var onCharacterClickListener: OnCharacterClickListener? = null

    private var filteredCharacters = mutableListOf<CharacterInfoView>()

    fun setFilteredCharacters(filtered: List<CharacterInfoView>) {
        submitList(filtered)
        filteredCharacters = ArrayList(filtered)
    }

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

    override fun getFilter(): Filter = FilterClass.filter(filteredCharacters, this)
}