package com.aston.rickandmorty.presentation.fragment.characters

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.aston.domain.usecase.FetchCharactersThoughDatabaseUseCase
import com.aston.domain.usecase.FetchCharactersThoughServiceUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.fragment.characters_filter.CharactersFilterFragment
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val FRAGMENT_FILTER_TAG = "CharacterFragmentFilter"

class CharactersViewModel @Inject constructor(
    private val context: Context,
    private val fetchCharactersThoughServiceUseCase: FetchCharactersThoughServiceUseCase,
    private val fetchCharactersThoughDatabaseUseCase: FetchCharactersThoughDatabaseUseCase,
    private val mapper: MapperCharacterView,
) : BaseViewModel() {

    fun charactersFlow(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfoView>> {
        return if (hasInternetConnection()) {
            fetchCharactersThoughService(characterName, characterStatus, characterSpecies, characterGender)
        } else {
            fetchCharactersThoughDatabase(characterName, characterStatus, characterSpecies, characterGender)
        }
    }

    private fun fetchCharactersThoughService(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfoView>> = fetchCharactersThoughServiceUseCase(
        characterName, characterStatus, characterSpecies, characterGender
    ).map { paging ->
        paging.map { character ->
            mapper.mapToCharacterInfoView(character)
        }
    }.cachedIn(viewModelScope)

    private fun fetchCharactersThoughDatabase(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ): Flow<PagingData<CharacterInfoView>> = fetchCharactersThoughDatabaseUseCase(
        characterName, characterStatus, characterSpecies, characterGender
    ).map { paging ->
        paging.map {
            mapper.mapToCharacterInfoView(it)
        }
    }.cachedIn(viewModelScope)

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capability = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capability.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun launchDialogFragment() {
        launchDialogFragment(CharactersFilterFragment.newInstance(), FRAGMENT_FILTER_TAG)
    }

    fun launchCharacterDetailsFragment(character: CharacterInfoView) {
        launchFragment(CharacterDetailsFragment.newInstance(character))
    }

}