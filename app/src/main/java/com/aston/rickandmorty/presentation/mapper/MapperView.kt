package com.aston.rickandmorty.presentation.mapper

import com.aston.domain.model.*
import com.aston.rickandmorty.presentation.model.*

class MapperView {

    fun mapToAllCharactersView(allCharacters: AllCharacters): AllCharactersView {
        return AllCharactersView(
            info = mapToInfoView(allCharacters.info),
            characterInfo = allCharacters.characterInfo.map {characterInfo ->
                mapToCharacterInfoView(characterInfo)
            }
        )
    }

    private fun mapToInfoView(info: Info): InfoView {
        return InfoView(
            count = info.count,
            next = info.next,
            pages = info.pages
        )
    }

    private fun mapToCharacterInfoView(characterInfo: CharacterInfo): CharacterInfoView {
        return CharacterInfoView(
            created = characterInfo.created,
            episode = characterInfo.episode,
            gender = characterInfo.gender,
            id = characterInfo.id,
            image = characterInfo.image,
            location = mapToLocationView(characterInfo.location),
            name = characterInfo.name,
            origin = mapToOriginView(characterInfo.origin),
            species = characterInfo.species,
            status = characterInfo.status
        )
    }

    private fun mapToLocationView(location: Location): LocationView {
        return LocationView(
            locationName = location.locationName,
            locationInfo = location.locationInfo
        )
    }

    private fun mapToOriginView(origin: Origin): OriginView {
        return OriginView(
            originLocationName = origin.originLocationName,
            originLocationInfo = origin.originLocationInfo
        )
    }


}