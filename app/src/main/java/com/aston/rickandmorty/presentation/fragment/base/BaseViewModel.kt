package com.aston.rickandmorty.presentation.fragment.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.presentation.util.Event
import com.aston.rickandmorty.presentation.util.NavigationManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseViewModel : ViewModel() {

    private val _manageFragmentLiveData = MutableLiveData<Event<NavigationManager>>()
    val manageFragmentLiveData: LiveData<Event<NavigationManager>> = _manageFragmentLiveData

    private val _exceptionLiveData = MutableLiveData<Int>()
    val exceptionLiveData: LiveData<Int> = _exceptionLiveData

    fun launchFragment(fragment: Fragment) {
        _manageFragmentLiveData.value = Event(NavigationManager.LaunchFragment(fragment))
    }

    fun launchDialogFragment(fragment: BottomSheetDialogFragment, fragmentTag: String) {
        _manageFragmentLiveData.value = Event(NavigationManager.LaunchDialogFragment(fragment, fragmentTag))
    }

    fun refreshFragment(fragment: Fragment) {
        _manageFragmentLiveData.value = Event(NavigationManager.RefreshFragment(fragment))
    }

    fun showExceptionMessage(@StringRes exceptionMessage: Int) {
        _exceptionLiveData.value = exceptionMessage
    }

    fun navigateBack() {
        _manageFragmentLiveData.value = Event(NavigationManager.NavigateBack)
    }
}