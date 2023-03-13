package com.aston.rickandmorty.presentation.fragment.character_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aston.domain.usecase.GetEpisodesForCharacterByUrlUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.mapper.MapperEpisodeView
import com.aston.rickandmorty.presentation.model.episode.EpisodeView
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val mapperEpisode: MapperEpisodeView,
    private val getEpisodesForCharacterByUrlUseCase: GetEpisodesForCharacterByUrlUseCase,
//    episodeUrl: String,
) : BaseViewModel() {

    private val _characterDetailsLiveData = MutableLiveData<List<EpisodeView>>()
    val characterDetailsLiveData: LiveData<List<EpisodeView>> = _characterDetailsLiveData

    fun fetchEpisodesForCharacterByUrl(episodesUrl: List<String>) {
        viewModelScope.launch {
            val episodesIds = episodesUrl.map { episodeUrl ->
                episodeUrl.split("/").last().toInt()
            }
            val episodes = getEpisodesForCharacterByUrlUseCase(episodesIds)
            _characterDetailsLiveData.value = episodes.map { episode ->
                mapperEpisode.mapToEpisodeView(episode)
            }
        }
    }
}