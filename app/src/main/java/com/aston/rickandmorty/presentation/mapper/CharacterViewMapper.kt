package com.aston.rickandmorty.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.character.Location
import com.aston.domain.model.character.Origin
import com.aston.rickandmorty.presentation.model.character.CharacterInfoViewModel
import com.aston.rickandmorty.presentation.model.character.LocationViewModel
import com.aston.rickandmorty.presentation.model.character.OriginViewModel
import javax.inject.Inject

class CharacterViewMapper @Inject constructor() {

    fun mapToCharactersListView(list: List<CharacterInfo>): List<CharacterInfoViewModel> {
        return list.map { character ->
            mapToCharacterInfoView(character)
        }
    }

    fun mapToCharacterPagingView(paging: PagingData<CharacterInfo>): PagingData<CharacterInfoViewModel> {
        return paging.map { character ->
            mapToCharacterInfoView(character)
        }
    }

    fun mapToCharacterInfoView(character: CharacterInfo): CharacterInfoViewModel {
        return CharacterInfoViewModel(
            created = character.created,
            episodes = character.episode,
            gender = character.gender,
            id = character.id,
            image = character.image,
            location = mapToLocationView(character.location),
            name = character.name,
            origin = mapToOriginView(character.origin),
            species = character.species,
            status = character.status,
        )
    }

    private fun mapToLocationView(location: Location): LocationViewModel {
        return LocationViewModel(
            locationName = location.locationName, locationInfo = location.locationInfo
        )
    }

    private fun mapToOriginView(origin: Origin): OriginViewModel {
        return OriginViewModel(
            originLocationName = origin.originLocationName,
            originLocationInfo = origin.originLocationInfo
        )
    }

}