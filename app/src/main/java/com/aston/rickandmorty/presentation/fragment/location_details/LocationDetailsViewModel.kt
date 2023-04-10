package com.aston.rickandmorty.presentation.fragment.location_details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.usecase.location.FetchCharactersByIdsUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.mapper.CharacterViewMapper
import com.aston.rickandmorty.presentation.model.character.CharacterInfoViewModel
import com.aston.rickandmorty.presentation.model.location.LocationInfoViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLIT = "/"

class LocationDetailsViewModel @Inject constructor(
    context: Context,
    private val fetchCharactersByIdsUseCase: FetchCharactersByIdsUseCase,
    private val mapperCharacter: CharacterViewMapper,
) : BaseViewModel(context) {

    private val _charactersLiveData = MutableLiveData<List<CharacterInfoViewModel>>()
    val charactersLiveData: LiveData<List<CharacterInfoViewModel>> = _charactersLiveData

    fun getCharacters(characterUrls: List<String>) {
        val characterIds = characterUrls.map { characterUrl ->
            characterUrl.split(SPLIT).last().toInt()
        }
        fetchCharacters(fetchCharactersByIdsUseCase(characterIds))
    }

    private fun fetchCharacters(
        useCase: Flow<List<CharacterInfo>>,
    ) {
        viewModelScope.launch {
            useCase
                .catch {
                    showExceptionMessage(R.string.fetchData_exceptionMessage)
                }
                .collectLatest { characters ->
                    _charactersLiveData.value = mapperCharacter.mapToCharactersListView(characters)
                }
        }
    }

    fun launchCharacterDetailsFragment(character: CharacterInfoViewModel) {
        launchFragment(CharacterDetailsFragment.newInstance(character))
    }

    fun refreshFragment(location: LocationInfoViewModel) {
        val fragment = LocationDetailsFragment.newInstance(location)
        refreshFragment(fragment)
    }

}