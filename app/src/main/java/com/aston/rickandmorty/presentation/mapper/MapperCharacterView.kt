package com.aston.rickandmorty.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.character.Location
import com.aston.domain.model.character.Origin
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import com.aston.rickandmorty.presentation.model.character.LocationView
import com.aston.rickandmorty.presentation.model.character.OriginView
import javax.inject.Inject

class MapperCharacterView @Inject constructor() {

    fun mapToCharacterPagingView(paging: PagingData<CharacterInfo>): PagingData<CharacterInfoView> {
        return paging.map { character ->
            mapToCharacterInfoView(character)
        }
    }

    private fun mapToCharacterInfoView(character: CharacterInfo): CharacterInfoView {
        return CharacterInfoView(
            created = character.created,
            episodes = character.episode,
            gender = character.gender,
            id = character.id,
            image = character.image,
            location = mapToLocationView(character.location),
            name = character.name,
            origin = mapToOriginView(character.origin),
            species = character.species,
            status = character.status
        )
    }

    private fun mapToLocationView(location: Location): LocationView {
        return LocationView(
            locationName = location.locationName, locationInfo = location.locationInfo
        )
    }

    private fun mapToOriginView(origin: Origin): OriginView {
        return OriginView(
            originLocationName = origin.originLocationName,
            originLocationInfo = origin.originLocationInfo
        )
    }

}