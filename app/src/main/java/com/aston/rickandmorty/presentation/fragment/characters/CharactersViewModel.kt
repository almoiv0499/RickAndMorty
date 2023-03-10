package com.aston.rickandmorty.presentation.fragment.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aston.data.mapper.MapperData
import com.aston.data.remote.RetrofitInstance
import com.aston.data.repository.CharactersRemoteRepositoryImpl
import com.aston.domain.usecase.GetAllCharactersUseCase
import com.aston.rickandmorty.presentation.mapper.MapperView
import com.aston.rickandmorty.presentation.model.AllCharactersView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private val mapper = MapperView()
    private val mapperData = MapperData()
    private val service = RetrofitInstance.charactersService
    private val repository = CharactersRemoteRepositoryImpl(service, mapperData)
    private val getAllCharactersUseCase = GetAllCharactersUseCase(repository, Dispatchers.IO)

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