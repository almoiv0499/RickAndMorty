package com.aston.rickandmorty.presentation.fragment.characters_filter

import com.aston.rickandmorty.presentation.fragment.base.BaseBottomSheetDialogViewModel
import com.aston.rickandmorty.presentation.fragment.characters.CharactersFragment
import javax.inject.Inject

class CharactersFilterViewModel @Inject constructor() : BaseBottomSheetDialogViewModel() {

    fun returnToFilteredFragment(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ) {
        launchFilteredFragment(
            CharactersFragment.newInstance(
                characterName, characterStatus, characterSpecies, characterGender
            )
        )
    }

}