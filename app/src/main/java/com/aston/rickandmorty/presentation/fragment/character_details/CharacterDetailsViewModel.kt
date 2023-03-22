package com.aston.rickandmorty.presentation.fragment.character_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.aston.domain.usecase.character.FetchEpisodesByIdsUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.episode_details.EpisodeDetailsFragment
import com.aston.rickandmorty.presentation.mapper.MapperEpisodeView
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val SPLIT = "/"

class CharacterDetailsViewModel @Inject constructor(
    private val fetchEpisodesByIdsUseCase: FetchEpisodesByIdsUseCase,
    private val mapperEpisode: MapperEpisodeView
) : BaseViewModel() {

    fun fetchEpisodeLiveData(episodesUrl: List<String>): LiveData<List<EpisodeInfoView>> {
        val episodesIds = episodesUrl.map { episodeUrl ->
            episodeUrl.split(SPLIT).last().toInt()
        }
        return fetchEpisodesByIdsUseCase(episodesIds).map { episodes ->
            episodes.map { episode ->
                mapperEpisode.mapToEpisodeView(episode)
            }
        }.asLiveData()
    }

    fun launchEpisodeDetailsFragment(episode: EpisodeInfoView) {
        launchFragment(EpisodeDetailsFragment.newInstance(episode))
    }

}