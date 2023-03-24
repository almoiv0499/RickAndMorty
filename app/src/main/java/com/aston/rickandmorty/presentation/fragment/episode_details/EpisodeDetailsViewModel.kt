package com.aston.rickandmorty.presentation.fragment.episode_details

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.usecase.episode.FetchCharactersByIdDatabaseUseCase
import com.aston.domain.usecase.episode.FetchCharactersByIdServiceUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val SPLIT = "/"

class EpisodeDetailsViewModel @Inject constructor(
    private val context: Context,
    private val fetchCharactersByIdServiceUseCase: FetchCharactersByIdServiceUseCase,
    private val fetchCharactersByIdDatabaseUseCase: FetchCharactersByIdDatabaseUseCase,
    private val mapperCharacter: MapperCharacterView,
) : BaseViewModel() {

    private val compositeDisposable by lazy(LazyThreadSafetyMode.NONE) {
        CompositeDisposable()
    }

    private val _charactersLiveData = MutableLiveData<List<CharacterInfoView>>()
    val charactersLiveData: LiveData<List<CharacterInfoView>> = _charactersLiveData

    fun fetchCharactersLiveData(characterUrls: List<String>) {
        val characterIds = characterUrls.map { characterUrl ->
            characterUrl.split(SPLIT).last().toInt()
        }
        if (hasInternetConnection()) {
            fetchCharactersForEpisode(fetchCharactersByIdServiceUseCase(characterIds))
        } else {
            fetchCharactersForEpisode(fetchCharactersByIdDatabaseUseCase(characterIds))
        }
    }

    private fun fetchCharactersForEpisode(
        useCase: Observable<List<CharacterInfo>>,
    ) {
        compositeDisposable.add(
            useCase.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ characters ->
                    _charactersLiveData.value = characters.map { character ->
                        mapperCharacter.mapToCharacterInfoView(character)
                    }
                }, {
                    showExceptionMessage(R.string.exception_message)
                })
        )
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

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun launchCharacterDetailsFragment(character: CharacterInfoView) {
        launchFragment(CharacterDetailsFragment.newInstance(character))
    }

}