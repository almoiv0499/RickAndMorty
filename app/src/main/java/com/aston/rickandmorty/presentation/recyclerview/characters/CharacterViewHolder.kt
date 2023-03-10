package com.aston.rickandmorty.presentation.recyclerview.characters

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aston.rickandmorty.databinding.CharacterItemBinding
import com.aston.rickandmorty.presentation.model.CharacterInfoView

class CharacterViewHolder(
    private val binding: CharacterItemBinding
) : ViewHolder(binding.root) {

    fun populate(character: CharacterInfoView) {
        with(binding) {
            characterName.text = character.name
            characterSpecies.text = character.species
            characterStatus.text = character.status
            characterGender.text = character.gender


        }
    }

}