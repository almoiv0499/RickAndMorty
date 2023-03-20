package com.aston.rickandmorty.presentation.fragment.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.presentation.util.Event
import com.aston.rickandmorty.presentation.util.Navigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseViewModel : ViewModel() {

    private val _launchFragmentLiveData = MutableLiveData<Event<Navigator>>()
    val launchFragmentLiveData: LiveData<Event<Navigator>> = _launchFragmentLiveData

    fun launchFragment(fragment: Fragment) {
        _launchFragmentLiveData.value = Event(Navigator.LaunchFragment(fragment))
    }

    fun launchDialogFragment(fragment: BottomSheetDialogFragment, fragmentTag: String) {
        _launchFragmentLiveData.value =
            Event(Navigator.LaunchDialogFragment(fragment, fragmentTag))
    }

    fun navigateBack() {
        _launchFragmentLiveData.value = Event(Navigator.NavigateBack)
    }
}