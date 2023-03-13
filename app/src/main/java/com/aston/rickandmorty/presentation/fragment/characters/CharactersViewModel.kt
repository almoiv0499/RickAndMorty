package com.aston.rickandmorty.presentation.fragment.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aston.domain.usecase.GetAllCharactersUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import com.aston.rickandmorty.presentation.model.character.CharactersResultView
import com.aston.rickandmorty.presentation.model.character.CharacterView
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val mapper: MapperCharacterView,
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : BaseViewModel() {

    private val _charactersLiveData = MutableLiveData<CharactersResultView>()
    val charactersLiveData: LiveData<CharactersResultView> = _charactersLiveData

    private val _statusLiveData = MutableLiveData<String>()
    val statusLiveData: LiveData<String> = _statusLiveData

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _charactersLiveData.value = mapper.mapToCharacterResultView(getAllCharactersUseCase())
        }
    }

    fun navigateToCharacterDetailsFragment(character: CharacterView) {
        navigateTo(CharacterDetailsFragment.newInstance(character))
    }

    fun saveStatus(status: String) {
        _statusLiveData.value = status
    }

}