package com.aston.rickandmorty.presentation.mapper

import com.aston.domain.model.character.*
import com.aston.rickandmorty.presentation.model.character.*
import javax.inject.Inject

class MapperCharacterView @Inject constructor() {

    fun mapToCharacterResultView(charactersResult: CharactersResult): CharactersResultView {
        return CharactersResultView(
            info = mapToInfoView(charactersResult.info),
            characterInfo = charactersResult.characterInfo.map { characterInfo ->
                mapToCharacterView(characterInfo)
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

    private fun mapToCharacterView(character: Character): CharacterView {
        return CharacterView(
            created = character.created,
            episode = character.episode,
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