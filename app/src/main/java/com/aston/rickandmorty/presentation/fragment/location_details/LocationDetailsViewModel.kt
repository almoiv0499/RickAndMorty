package com.aston.rickandmorty.presentation.fragment.location_details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.usecase.location.FetchCharactersByIdUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import com.aston.rickandmorty.presentation.model.location.LocationInfoView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLIT = "/"

class LocationDetailsViewModel @Inject constructor(
    private val context: Context,
    private val fetchCharactersByIdUseCase: FetchCharactersByIdUseCase,
    private val mapperCharacter: MapperCharacterView,
) : BaseViewModel(context) {

    private val _charactersLiveData = MutableLiveData<List<CharacterInfoView>>()
    val charactersLiveData: LiveData<List<CharacterInfoView>> = _charactersLiveData

    fun fetchCharactersLiveData(characterUrls: List<String>) {
        val characterIds = characterUrls.map { characterUrl ->
            characterUrl.split(SPLIT).last().toInt()
        }
        fetchCharacters(fetchCharactersByIdUseCase(characterIds))
    }

    private fun fetchCharacters(
        useCase: Flow<List<CharacterInfo>>,
    ) {
        viewModelScope.launch {
            useCase.catch {
                showExceptionMessage(R.string.exception_message)
            }.collectLatest { characters ->
                _charactersLiveData.value = characters.map { character ->
                    mapperCharacter.mapToCharacterInfoView(character)
                }
            }
        }
    }

    fun launchCharacterDetailsFragment(character: CharacterInfoView) {
        launchFragment(CharacterDetailsFragment.newInstance(character))
    }

    fun refreshFragment(location: LocationInfoView) {
        val fragment = LocationDetailsFragment.newInstance(location)
        refreshFragment(fragment)
    }

}