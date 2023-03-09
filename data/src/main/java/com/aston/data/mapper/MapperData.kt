package com.aston.data.mapper

import com.aston.data.model.*
import com.aston.domain.model.*

class MapperData {

    fun mapToAllCharacters(allCharactersData: AllCharactersData): AllCharacters {
        return AllCharacters(
            info = mapToInfo(allCharactersData.info),
            characterInfo = allCharactersData.characterInfo.map {characterInfoData ->
                mapToCharacterInfo(characterInfoData)
            }
        )
    }

    private fun mapToInfo(infoData: InfoData): Info {
        return Info(
            count = infoData.count,
            next = infoData.next,
            pages = infoData.pages,
            prev = infoData.prev
        )
    }

    private fun mapToCharacterInfo(characterInfoData: CharacterInfoData): CharacterInfo {
        return CharacterInfo(
            created = characterInfoData.created,
            episode = characterInfoData.episode,
            gender = characterInfoData.gender,
            id = characterInfoData.id,
            image = characterInfoData.image,
            location = mapToLocation(characterInfoData.location),
            name = characterInfoData.name,
            origin = mapToOrigin(characterInfoData.origin),
            species = characterInfoData.species,
            status = characterInfoData.status
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