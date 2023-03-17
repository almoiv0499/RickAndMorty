package com.aston.rickandmorty.presentation.fragment.characters

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.aston.domain.usecase.FetchCharactersThoughDatabaseUseCase
import com.aston.domain.usecase.FetchCharactersThoughServiceUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val application: Application,
    fetchCharactersThoughServiceUseCase: FetchCharactersThoughServiceUseCase,
    fetchCharactersThoughDatabaseUseCase: FetchCharactersThoughDatabaseUseCase,
    private val mapper: MapperCharacterView,
) : BaseViewModel() {

    fun charactersLiveData(): LiveData<PagingData<CharacterInfoView>> {
        return if (hasInternetConnection()) {
            characters
        } else {
            charactersDatabase
        }
    }

    private val characters: LiveData<PagingData<CharacterInfoView>> =
        fetchCharactersThoughServiceUseCase().asLiveData().map { paging ->
            paging.map { character ->
                mapper.mapToCharacterInfoView(character)
            }
        }.cachedIn(viewModelScope)

    private val charactersDatabase: LiveData<PagingData<CharacterInfoView>> =
        fetchCharactersThoughDatabaseUseCase().asLiveData().map { paging ->
                paging.map {
                    mapper.mapToCharacterInfoView(it)
                }
            }.cachedIn(viewModelScope)

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = application.getSystemService(ConnectivityManager::class.java)
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capability = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capability.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false
        }
    }

    fun navigateToCharacterDetailsFragment(character: CharacterInfoView) {
        navigateTo(CharacterDetailsFragment.newInstance(character))
    }

}