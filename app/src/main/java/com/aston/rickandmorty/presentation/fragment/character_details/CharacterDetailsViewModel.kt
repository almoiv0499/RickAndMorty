package com.aston.rickandmorty.presentation.fragment.character_details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.model.location.LocationInfo
import com.aston.domain.usecase.character.*
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.episode_details.EpisodeDetailsFragment
import com.aston.rickandmorty.presentation.fragment.location_details.LocationDetailsFragment
import com.aston.rickandmorty.presentation.mapper.EpisodeViewMapper
import com.aston.rickandmorty.presentation.mapper.LocationViewMapper
import com.aston.rickandmorty.presentation.model.character.CharacterInfoViewModel
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoViewModel
import com.aston.rickandmorty.presentation.model.location.LocationInfoViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLIT = "/"

class CharacterDetailsViewModel @Inject constructor(
    context: Context,
    private val fetchEpisodesByIdsUseCase: FetchEpisodesByIdsUseCase,
    private val fetchLocationByIdServiceUseCase: FetchLocationByIdServiceUseCase,
    private val fetchLocationByIdDatabaseUseCase: FetchLocationByIdDatabaseUseCase,
    private val fetchOriginLocationByNameService: FetchOriginLocationByNameService,
    private val fetchOriginLocationByNameDatabase: FetchOriginLocationByNameDatabaseUseCase,
    private val mapperEpisode: EpisodeViewMapper,
    private val mapperLocation: LocationViewMapper,
) : BaseViewModel(context) {

    private val _episodesLiveData = MutableLiveData<List<EpisodeInfoViewModel>>()
    val episodesLiveData: LiveData<List<EpisodeInfoViewModel>> = _episodesLiveData

    private val _locationLiveData = MutableLiveData<LocationInfoViewModel>()
    val locationLiveData: LiveData<LocationInfoViewModel> = _locationLiveData

    private val _originLocationLiveData = MutableLiveData<LocationInfoViewModel>()
    val originLocationLiveData: LiveData<LocationInfoViewModel> = _originLocationLiveData

    fun getEpisodes(episodesUrl: List<String>) {
        val episodesIds = episodesUrl.map { episodeUrl ->
            episodeUrl.split(SPLIT).last().toInt()
        }
        fetchEpisodes(fetchEpisodesByIdsUseCase(episodesIds))
    }

    fun getLocationById(locationUrl: String) {
        val locationId = locationUrl.split(SPLIT).last().toInt()
        viewModelScope.launch {
            if (hasInternetConnection()) {
                _locationLiveData.value =
                    mapperLocation.mapToLocationInfoView(fetchLocationByIdServiceUseCase(locationId))
            } else fetchLocation(fetchLocationByIdDatabaseUseCase(locationId))
        }
    }

    fun getOriginLocationById(originLocationName: String) {
        viewModelScope.launch {
            if (hasInternetConnection()) {
                _originLocationLiveData.value = mapperLocation.mapToLocationInfoView(
                    fetchOriginLocationByNameService(originLocationName)
                )
            } else {
                fetchOriginLocationByNameDatabase(originLocationName)
                    .catch {
                        showExceptionMessage(R.string.fetchData_exceptionMessage)
                    }
                    .collectLatest { location ->
                        _originLocationLiveData.value =
                            mapperLocation.mapToLocationInfoView(location)
                    }
            }
        }
    }

    private fun fetchLocation(
        useCase: Flow<LocationInfo>,
    ) {
        viewModelScope.launch {
            useCase
                .catch {
                    showExceptionMessage(R.string.fetchData_exceptionMessage)
                }
                .collectLatest { location ->
                    _locationLiveData.value = mapperLocation.mapToLocationInfoView(location)
                }
        }
    }

    private fun fetchEpisodes(
        useCase: Flow<List<EpisodeInfo>>,
    ) {
        viewModelScope.launch {
            useCase
                .catch {
                    showExceptionMessage(R.string.fetchData_exceptionMessage)
                }
                .collectLatest { episodes ->
                    _episodesLiveData.value = mapperEpisode.mapToEpisodeListView(episodes)
                }
        }
    }

    fun launchEpisodeDetailsFragment(episode: EpisodeInfoViewModel) {
        launchFragment(EpisodeDetailsFragment.newInstance(episode))
    }

    fun launchLocationDetailsFragment(location: LocationInfoViewModel) {
        launchFragment(LocationDetailsFragment.newInstance(location))
    }

    fun refreshFragment(character: CharacterInfoViewModel) {
        val fragment = CharacterDetailsFragment.newInstance(character)
        refreshFragment(fragment)
    }

}