package com.aston.rickandmorty.presentation.fragment.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aston.domain.usecase.GetAllCharactersUseCase
import com.aston.rickandmorty.presentation.mapper.MapperView
import com.aston.rickandmorty.presentation.model.AllCharactersView
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val mapper: MapperView,
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private val _charactersLiveData = MutableLiveData<AllCharactersView>()
    val charactersLiveData: LiveData<AllCharactersView> = _charactersLiveData

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _charactersLiveData.value = mapper.mapToAllCharactersView(getAllCharactersUseCase())
        }
    }

}