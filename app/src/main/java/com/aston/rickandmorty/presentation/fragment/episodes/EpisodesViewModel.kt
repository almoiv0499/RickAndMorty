package com.aston.rickandmorty.presentation.fragment.episodes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.usecase.episode.FetchEpisodeThoughDatabaseUseCase
import com.aston.domain.usecase.episode.FetchEpisodeThoughServiceUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.episode_details.EpisodeDetailsFragment
import com.aston.rickandmorty.presentation.fragment.episode_filter.EpisodesFilterFragment
import com.aston.rickandmorty.presentation.mapper.MapperEpisodeView
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

private const val FRAGMENT_FILTER_TAG = "EpisodeFragmentFilter"

class EpisodesViewModel @Inject constructor(
    private val context: Context,
    private val fetchEpisodeThoughDatabaseUseCase: FetchEpisodeThoughDatabaseUseCase,
    private val fetchEpisodeThoughServiceUseCase: FetchEpisodeThoughServiceUseCase,
    private val mapperEpisode: MapperEpisodeView,
) : BaseViewModel(context) {

    private val compositeDisposable by lazy(LazyThreadSafetyMode.NONE) {
        CompositeDisposable()
    }
    private val _episodesLD = MutableLiveData<PagingData<EpisodeInfoView>>()
    val episodesLiveData: LiveData<PagingData<EpisodeInfoView>> = _episodesLD

    fun fetch(episodeName: String, episodeNumber: String) {
        if (hasInternetConnection()) {
            fetchEpisodes(fetchEpisodeThoughServiceUseCase(episodeName, episodeNumber))
        } else {
            fetchEpisodes(fetchEpisodeThoughDatabaseUseCase(episodeName, episodeNumber))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchEpisodes(
        useCase: Observable<PagingData<EpisodeInfo>>,
    ) {
        compositeDisposable.add(
            useCase
                .cachedIn(viewModelScope)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ paging ->
                    _episodesLD.value = mapperEpisode.mapToEpisodePaging(paging)
                }, {
                    showExceptionMessage(R.string.exception_message)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun launchFilterFragment() {
        launchDialogFragment(EpisodesFilterFragment.newInstance(), FRAGMENT_FILTER_TAG)
    }

    fun launchEpisodeDetailsFragment(episode: EpisodeInfoView) {
        launchFragment(EpisodeDetailsFragment.newInstance(episode))
    }

    fun refreshFragment() {
        refreshFragment(EpisodesFragment.newInstance())
    }

}