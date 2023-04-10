package com.aston.rickandmorty.presentation.fragment.episode_details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aston.domain.model.character.CharacterInfo
import com.aston.domain.usecase.episode.FetchCharactersByIdsDatabaseUseCase
import com.aston.domain.usecase.episode.FetchCharactersByIdsServiceUseCase
import com.aston.rickandmorty.R
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.fragment.character_details.CharacterDetailsFragment
import com.aston.rickandmorty.presentation.mapper.CharacterViewMapper
import com.aston.rickandmorty.presentation.model.character.CharacterInfoViewModel
import com.aston.rickandmorty.presentation.model.episode.EpisodeInfoViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

private const val SPLIT = "/"

class EpisodeDetailsViewModel @Inject constructor(
    context: Context,
    private val fetchCharactersByIdsServiceUseCase: FetchCharactersByIdsServiceUseCase,
    private val fetchCharactersByIdsDatabaseUseCase: FetchCharactersByIdsDatabaseUseCase,
    private val mapperCharacter: CharacterViewMapper,
) : BaseViewModel(context) {

    private val compositeDisposable by lazy(LazyThreadSafetyMode.NONE) {
        CompositeDisposable()
    }

    private val _charactersLiveData = MutableLiveData<List<CharacterInfoViewModel>>()
    val charactersLiveData: LiveData<List<CharacterInfoViewModel>> = _charactersLiveData

    fun getCharacters(characterUrls: List<String>) {
        val characterIds = characterUrls.map { characterUrl ->
            characterUrl.split(SPLIT).last().toInt()
        }
        if (hasInternetConnection()) {
            fetchCharactersForEpisode(fetchCharactersByIdsServiceUseCase(characterIds))
        } else {
            fetchCharactersForEpisode(fetchCharactersByIdsDatabaseUseCase(characterIds))
        }
    }

    private fun fetchCharactersForEpisode(
        useCase: Observable<List<CharacterInfo>>,
    ) {
        compositeDisposable.add(
            useCase
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ characters ->
                    _charactersLiveData.value = mapperCharacter.mapToCharactersListView(characters)
                }, {
                    showExceptionMessage(R.string.fetchData_exceptionMessage)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun launchCharacterDetailsFragment(character: CharacterInfoViewModel) {
        launchFragment(CharacterDetailsFragment.newInstance(character))
    }

    fun refreshEpisodeDetailsFragment(episode: EpisodeInfoViewModel) {
        val fragment = EpisodeDetailsFragment.newInstance(episode)
        refreshFragment(fragment)
    }

}