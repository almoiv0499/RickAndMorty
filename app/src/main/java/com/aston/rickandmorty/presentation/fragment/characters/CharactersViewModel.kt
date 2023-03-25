package com.aston.rickandmorty.presentation.fragment.characters

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.usecase.character.FetchCharactersThoughDatabaseUseCase
import com.aston.domain.usecase.character.FetchCharactersThoughServiceUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.fragment.characters_filter.CharactersFilterFragment
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FRAGMENT_FILTER_TAG = "CharacterFragmentFilter"

class CharactersViewModel @Inject constructor(
    private val context: Context,
    private val fetchCharactersThoughServiceUseCase: FetchCharactersThoughServiceUseCase,
    private val fetchCharactersThoughDatabaseUseCase: FetchCharactersThoughDatabaseUseCase,
    private val mapper: MapperCharacterView,
) : BaseViewModel(context) {

    private val _charactersLiveData = MutableLiveData<PagingData<CharacterInfoView>>()
    val charactersLiveData: LiveData<PagingData<CharacterInfoView>> = _charactersLiveData

    fun charactersFlow(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ) {
        if (hasInternetConnection()) {
            fetchCharacters(
                fetchCharactersThoughServiceUseCase(
                    characterName, characterStatus, characterSpecies, characterGender
                )
            )
        } else {
            fetchCharacters(
                fetchCharactersThoughDatabaseUseCase(
                    characterName, characterStatus, characterSpecies, characterGender
                )
            )
        }
    }

    private fun fetchCharacters(
        useCase: Flow<PagingData<CharacterInfo>>,
    ) {
        viewModelScope.launch {
            useCase.cachedIn(viewModelScope).collectLatest { paging ->
                _charactersLiveData.value = mapper.mapToCharacterPagingView(paging)
            }
        }
    }

    fun launchCharacterDetailsFragment(character: CharacterInfoView) {
        launchFragment(CharacterDetailsFragment.newInstance(character))
    }

    fun launchDialogFragment() {
        launchDialogFragment(CharactersFilterFragment.newInstance(), FRAGMENT_FILTER_TAG)
    }

    fun refreshFragment() {
        refreshFragment(CharactersFragment.newInstance())
    }

}