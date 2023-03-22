package com.aston.rickandmorty.presentation.fragment.episode_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aston.domain.usecase.episode.FetchCharactersByIdsUseCase
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModel
import com.aston.rickandmorty.presentation.mapper.MapperCharacterView
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val SPLIT = "/"

class EpisodeDetailsViewModel @Inject constructor(
    private val fetchCharactersByIdsUseCase: FetchCharactersByIdsUseCase,
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
        compositeDisposable.add(fetchCharactersByIdsUseCase(characterIds).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe { characters ->
                _charactersLiveData.value = characters.map { character ->
                    mapperCharacter.mapToCharacterInfoView(character)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

}