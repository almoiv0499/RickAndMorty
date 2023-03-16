package com.aston.data.util.mapper

import com.aston.data.model.character.*
import com.aston.domain.model.character.*
import javax.inject.Inject

class MapperCharacterData @Inject constructor() {

    fun mapToCharactersResult(characterResultData: CharactersResultData): CharactersResult {
        return CharactersResult(
            info = mapToInfo(characterResultData.info),
            characterInfo = characterResultData.characterInfo.map { characterInfoData ->
                mapToCharacter(characterInfoData)
            }
        )
    }

    private fun mapToInfo(infoData: InfoData): Info {
        return Info(
            count = infoData.count,
            next = infoData.next,
            pages = infoData.pages
        )
    }

    fun mapToCharacter(characterData: CharacterData): CharacterDetails {
        return CharacterDetails(
            created = characterData.created,
            episode = characterData.episode,
            gender = characterData.gender,
            id = characterData.id,
            image = characterData.image,
            location = mapToLocation(characterData.location),
            name = characterData.name,
            origin = mapToOrigin(characterData.origin),
            species = characterData.species,
            status = characterData.status
        )
    }

    private fun mapToLocation(locationData: LocationData): Location {
        return Location(
            locationName = locationData.locationName,
            locationInfo = locationData.locationInfo
        )
    }

    private fun mapToOrigin(originData: OriginData): Origin {
        return Origin(
            originLocationName = originData.originLocationName,
            originLocationInfo = originData.originLocationInfo
        )
    }

}