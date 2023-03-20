package com.aston.rickandmorty.presentation.fragment.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.presentation.util.Event
import com.aston.rickandmorty.presentation.util.NavigatorForDialogFragment

abstract class BaseBottomSheetDialogViewModel : ViewModel() {

    private val _launchFilteredFragmentLiveData =
        MutableLiveData<Event<NavigatorForDialogFragment>>()
    val launchFilteredFragmentLiveData: LiveData<Event<NavigatorForDialogFragment>> =
        _launchFilteredFragmentLiveData

    fun launchFilteredFragment(fragment: Fragment) {
        _launchFilteredFragmentLiveData.value = Event(NavigatorForDialogFragment(fragment))
    }

}