package com.aston.rickandmorty.presentation.fragment.episode_filter

import com.aston.rickandmorty.presentation.fragment.base.BaseBottomSheetDialogViewModel
import com.aston.rickandmorty.presentation.fragment.episodes.EpisodesFragment
import javax.inject.Inject

class EpisodesFilterViewModel @Inject constructor() : BaseBottomSheetDialogViewModel() {

    fun launchFilteredEpisodesFragment(episodeName: String, episodeNumber: String) {
        launchFilteredFragment(EpisodesFragment.newInstance(episodeName, episodeNumber))
    }

}