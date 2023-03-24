package com.aston.rickandmorty.presentation.fragment.character_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.usecase.character.FetchEpisodesByIdsUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.episode_details.EpisodeDetailsFragment
import com.aston.rickandmorty.presentation.mapper.MapperEpisodeView
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLIT = "/"

class CharacterDetailsViewModel @Inject constructor(
    private val fetchEpisodesByIdsUseCase: FetchEpisodesByIdsUseCase,
    private val mapperEpisode: MapperEpisodeView,
) : BaseViewModel() {

    private val _episodesLiveData = MutableLiveData<List<EpisodeInfoView>>()
    val episodesLiveData: LiveData<List<EpisodeInfoView>> = _episodesLiveData

    fun fetchEpisodeLiveData(episodesUrl: List<String>) {
        val episodesIds = episodesUrl.map { episodeUrl ->
            episodeUrl.split(SPLIT).last().toInt()
        }
        fetchEpisodes(fetchEpisodesByIdsUseCase(episodesIds))
    }

    private fun fetchEpisodes(
        useCase: Flow<List<EpisodeInfo>>,
    ) {
        viewModelScope.launch {
            useCase.catch {
                showExceptionMessage(R.string.exception_message)
            }.collectLatest { episodes ->
                _episodesLiveData.value = episodes.map { episode ->
                    mapperEpisode.mapToEpisodeView(episode)
                }
            }
        }
    }

    fun launchEpisodeDetailsFragment(episode: EpisodeInfoView) {
        launchFragment(EpisodeDetailsFragment.newInstance(episode))
    }

}