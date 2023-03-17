package com.aston.rickandmorty.presentation.recyclerview.characters

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aston.rickandmorty.R
import com.aston.rickandmorty.databinding.CharacterItemBinding
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import com.bumptech.glide.Glide

class CharacterViewHolder(
    private val binding: CharacterItemBinding,
) : ViewHolder(binding.root) {

    fun populate(
        character: CharacterInfoView,
        onCharacterClickListener: OnCharacterClickListener?,
    ) {
        with(binding) {
            characterName.text = character.name
            characterSpecies.text = character.species
            characterStatus.text = character.status
            characterGender.text = character.gender

            Glide.with(root).load(character.image).placeholder(R.drawable.ic_launcher_foreground)
                .into(characterImage)

            characterCardView.setOnClickListener {
                onCharacterClickListener?.invoke(character)
            }
        }
    }

}