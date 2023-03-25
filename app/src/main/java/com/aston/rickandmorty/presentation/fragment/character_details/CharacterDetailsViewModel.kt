package com.aston.rickandmorty.presentation.fragment.character_details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.usecase.character.FetchEpisodesByIdsUseCase
import com.aston.domain.usecase.character.FetchLocationByIdUseCase
import com.aston.domain.usecase.character.FetchOriginLocationByNameUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.episode_details.EpisodeDetailsFragment
import com.aston.rickandmorty.presentation.fragment.location_details.LocationDetailsFragment
import com.aston.rickandmorty.presentation.mapper.MapperEpisodeView
import com.aston.rickandmorty.presentation.mapper.MapperLocationView
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView
import com.aston.rickandmorty.presentation.model.location.LocationInfoView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLIT = "/"

class CharacterDetailsViewModel @Inject constructor(
    context: Context,
    private val fetchEpisodesByIdsUseCase: FetchEpisodesByIdsUseCase,
    private val fetchLocationByIdUseCase: FetchLocationByIdUseCase,
    private val fetchOriginLocationByNameUseCase: FetchOriginLocationByNameUseCase,
    private val mapperEpisode: MapperEpisodeView,
    private val mapperLocation: MapperLocationView,
) : BaseViewModel(context) {

    private val _episodesLiveData = MutableLiveData<List<EpisodeInfoView>>()
    val episodesLiveData: LiveData<List<EpisodeInfoView>> = _episodesLiveData

    private val _locationLiveData = MutableLiveData<LocationInfoView>()
    val locationLiveData: LiveData<LocationInfoView> = _locationLiveData

    private val _originLocationLiveData = MutableLiveData<LocationInfoView>()
    val originLocationLiveData: LiveData<LocationInfoView> = _originLocationLiveData

    fun fetchEpisodeLiveData(episodesUrl: List<String>) {
        val episodesIds = episodesUrl.map { episodeUrl ->
            episodeUrl.split(SPLIT).last().toInt()
        }
        fetchEpisodes(fetchEpisodesByIdsUseCase(episodesIds))
    }

    fun fetchLocationById(locationUrl: String) {
        val locationId = locationUrl.split(SPLIT).last().toInt()
        viewModelScope.launch {
            fetchLocationByIdUseCase(locationId).catch {
                showExceptionMessage(R.string.exception_message)
            }.collectLatest { location ->
                _locationLiveData.value = mapperLocation.mapToLocationInfoView(location)
            }
        }
    }

    fun fetchOriginLocationById(originLocationName: String) {
        viewModelScope.launch {
            fetchOriginLocationByNameUseCase(originLocationName).catch {
                showExceptionMessage(R.string.exception_message)
            }.collectLatest { location ->
                _originLocationLiveData.value = mapperLocation.mapToLocationInfoView(location)
            }
        }
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

    fun launchLocationDetailsFragment(location: LocationInfoView) {
        launchFragment(LocationDetailsFragment.newInstance(location))
    }

}