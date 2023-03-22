package com.aston.rickandmorty.presentation.fragment.episodes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.aston.domain.usecase.episode.FetchEpisodeThoughDatabaseUseCase
import com.aston.domain.usecase.episode.FetchEpisodeThoughServiceUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.episode_filter.EpisodesFilterFragment
import com.aston.rickandmorty.presentation.mapper.MapperEpisodeView
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val FRAGMENT_FILTER_TAG = "EpisodeFragmentFilter"

class EpisodesViewModel @Inject constructor(
    private val context: Context,
    private val fetchEpisodeThoughDatabaseUseCase: FetchEpisodeThoughDatabaseUseCase,
    private val fetchEpisodeThoughServiceUseCase: FetchEpisodeThoughServiceUseCase,
    private val mapperEpisode: MapperEpisodeView,
) : BaseViewModel() {

    private val compositeDisposable by lazy(LazyThreadSafetyMode.NONE) {
        CompositeDisposable()
    }
    private val _episodesLD = MutableLiveData<PagingData<EpisodeInfoView>>()
    val episodesLD: LiveData<PagingData<EpisodeInfoView>> = _episodesLD

    fun fetch(episodeName: String, episodeNumber: String) {
        if (hasInternetConnection()) {
            compositeDisposable.add(fetchEpisodeThoughServiceUseCase(
                episodeName, episodeNumber
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { paging ->
                    _episodesLD.value = mapperEpisode.mapToEpisodePaging(paging)
                })
        } else {
            compositeDisposable.add(fetchEpisodeThoughDatabaseUseCase(
                episodeName, episodeNumber
            ).subscribeOn(
                Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe { paging ->
                _episodesLD.value = mapperEpisode.mapToEpisodePaging(paging)
            })
        }
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capability = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capability.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun launchFilterFragment() {
        launchDialogFragment(EpisodesFilterFragment.newInstance(), FRAGMENT_FILTER_TAG)
    }

}