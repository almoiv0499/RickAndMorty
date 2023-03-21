package com.aston.rickandmorty.presentation.fragment.location_details

import com.aston.domain.usecase.location.FetchCharactersByIdUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val SPLIT = "/"

class LocationDetailsViewModel @Inject constructor(
    private val fetchCharactersByIdUseCase: FetchCharactersByIdUseCase,
    private val mapperCharacter: MapperCharacterView,
) : BaseViewModel() {

    fun fetchCharactersLiveData(characterUrls: List<String>): Flow<List<CharacterInfoView>> {
        val characterIds = characterUrls.map { characterUrl ->
            characterUrl.split(SPLIT).last().toInt()
        }
        return mapperCharacter.mapToFlowListView(fetchCharactersByIdUseCase(characterIds))
    }

}