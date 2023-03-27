package com.aston.rickandmorty.presentation.fragment.episodes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.aston.domain.model.episode.EpisodeInfo
import com.aston.domain.usecase.episode.FetchEpisodesThoughDatabaseUseCase
import com.aston.domain.usecase.episode.FetchEpisodesThoughServiceUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.episode_details.EpisodeDetailsFragment
import com.aston.rickandmorty.presentation.fragment.episode_filter.EpisodesFilterFragment
import com.aston.rickandmorty.presentation.mapper.EpisodeViewMapper
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

private const val FRAGMENT_FILTER_TAG = "EpisodeFragmentFilter"

class EpisodesViewModel @Inject constructor(
    context: Context,
    private val fetchEpisodesThoughDatabaseUseCase: FetchEpisodesThoughDatabaseUseCase,
    private val fetchEpisodesThoughServiceUseCase: FetchEpisodesThoughServiceUseCase,
    private val mapperEpisode: EpisodeViewMapper,
) : BaseViewModel(context) {

    private val compositeDisposable by lazy(LazyThreadSafetyMode.NONE) {
        CompositeDisposable()
    }
    private val _episodesLiveData = MutableLiveData<PagingData<EpisodeInfoViewModel>>()
    val episodesLiveData: LiveData<PagingData<EpisodeInfoViewModel>> = _episodesLiveData

    fun getEpisodes(episodeName: String, episodeNumber: String) {
        if (hasInternetConnection()) {
            fetchEpisodes(fetchEpisodesThoughServiceUseCase(episodeName, episodeNumber))
        } else {
            fetchEpisodes(fetchEpisodesThoughDatabaseUseCase(episodeName, episodeNumber))
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
                    _episodesLiveData.value = mapperEpisode.mapToEpisodePagingView(paging)
                }, {
                    showExceptionMessage(R.string.fetchData_exceptionMessage)
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

    fun launchEpisodeDetailsFragment(episode: EpisodeInfoViewModel) {
        launchFragment(EpisodeDetailsFragment.newInstance(episode))
    }

    fun refreshFragment() {
        refreshFragment(EpisodesFragment.newInstance())
    }

}