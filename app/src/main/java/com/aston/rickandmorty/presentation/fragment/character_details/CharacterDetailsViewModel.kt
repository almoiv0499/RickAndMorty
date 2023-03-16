package com.aston.rickandmorty.presentation.fragment.character_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.aston.domain.usecase.FetchEpisodesByIdsUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.mapper.MapperEpisodeView
import com.aston.rickandmorty.presentation.model.episode.EpisodeView
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val mapperEpisode: MapperEpisodeView,
    private val fetchEpisodesByIdsUseCase: FetchEpisodesByIdsUseCase,
) : BaseViewModel() {

    fun episodeLiveData(episodesUrl: List<String>): LiveData<List<EpisodeView>> {
        val episodesIds = episodesUrl.map { episodeUrl ->
            episodeUrl.split("/").last().toInt()
        }
        return fetchEpisodesByIdsUseCase(episodesIds).map { episodes ->
            episodes.map { episode ->
                mapperEpisode.mapToEpisodeView(episode)
            }
        }.asLiveData()
    }
}