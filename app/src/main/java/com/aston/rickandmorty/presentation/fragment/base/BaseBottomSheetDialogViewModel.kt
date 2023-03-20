package com.aston.rickandmorty.presentation.fragment.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.presentation.util.Event
import com.aston.rickandmorty.presentation.util.NavigatorBottomSheetDialogFragment

abstract class BaseBottomSheetDialogViewModel : ViewModel() {

    private val _navigationToFilteredFragment =
        MutableLiveData<Event<NavigatorBottomSheetDialogFragment>>()
    val navigationToFilteredFragment: LiveData<Event<NavigatorBottomSheetDialogFragment>> =
        _navigationToFilteredFragment

    fun navigateToFilteredFragment(fragment: Fragment) {
        _navigationToFilteredFragment.value = Event(NavigatorBottomSheetDialogFragment(fragment))
    }

}