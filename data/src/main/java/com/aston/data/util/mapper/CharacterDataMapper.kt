package com.aston.data.util.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.aston.data.model.character.CharacterInfoData
import com.aston.data.model.character.LocationData
import com.aston.data.model.character.OriginData
import com.aston.data.model.character_dto.CharacterInfoDataDto
import com.aston.data.model.character_dto.LocationDataDto
import com.aston.data.model.character_dto.OriginDataDto
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.model.character.Location
import com.aston.domain.model.character.Origin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterDataMapper @Inject constructor() {

    fun mapToCharacterListData(list: List<CharacterInfoDataDto>): List<CharacterInfoData> {
        return list.map { character ->
            mapToCharacterData(character)
        }
    }

    fun mapToCharacterData(character: CharacterInfoDataDto): CharacterInfoData {
        return CharacterInfoData(
            created = character.characterCreated,
            episode = character.episodes,
            gender = character.characterGender,
            id = character.characterId,
            image = character.characterImage,
            location = mapToLocationData(character.location),
            name = character.characterName,
            origin = mapToOriginData(character.origin),
            species = character.characterSpecies,
            status = character.characterStatus
        )
    }

    private fun mapToLocationData(location: LocationDataDto): LocationData {
        return LocationData(
            locationName = location.locationName, locationInfo = location.locationInfo
        )
    }

    private fun mapToOriginData(origin: OriginDataDto): OriginData {
        return OriginData(
            originLocationName = origin.originLocationName,
            originLocationInfo = origin.originLocationInfo
        )
    }

    fun mapToCharacterFlowPaging(flow: Flow<PagingData<CharacterInfoData>>): Flow<PagingData<CharacterInfo>> {
        return flow.map { paging ->
            mapToCharacterPaging(paging)
        }
    }

    private fun mapToCharacterPaging(paging: PagingData<CharacterInfoData>): PagingData<CharacterInfo> {
        return paging.map { character ->
            mapToCharacter(character)
        }
    }

    fun mapToCharacterFlowList(flow: Flow<List<CharacterInfoData>>): Flow<List<CharacterInfo>> {
        return flow.map { characters ->
            characters.map { character ->
                mapToCharacter(character)
            }
        }
    }

    fun mapToCharacterList(list: List<CharacterInfoData>): List<CharacterInfo> {
        return list.map { character ->
            mapToCharacter(character)
        }
    }

    private fun mapToCharacter(characterData: CharacterInfoData): CharacterInfo {
        return CharacterInfo(
            created = characterData.created,
            episode = characterData.episode,
            gender = characterData.gender,
            id = characterData.id,
            image = characterData.image,
            location = mapToLocation(characterData.location),
            name = characterData.name,
            origin = mapToOrigin(characterData.origin),
            species = characterData.species,
            status = characterData.status,
        )
    }

    private fun mapToLocation(locationData: LocationData): Location {
        return Location(
            locationName = locationData.locationName, locationInfo = locationData.locationInfo
        )
    }

    private fun mapToOrigin(originData: OriginData): Origin {
        return Origin(
            originLocationName = originData.originLocationName,
            originLocationInfo = originData.originLocationInfo
        )
    }

}