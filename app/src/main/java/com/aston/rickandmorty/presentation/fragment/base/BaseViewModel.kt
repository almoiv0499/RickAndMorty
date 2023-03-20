package com.aston.rickandmorty.presentation.fragment.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.presentation.util.Event
import com.aston.rickandmorty.presentation.util.Navigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseViewModel : ViewModel() {

    private val _navigationToLiveData = MutableLiveData<Event<Navigator>>()
    val navigationToLiveData: LiveData<Event<Navigator>> = _navigationToLiveData

    fun navigateTo(fragment: Fragment) {
        _navigationToLiveData.value = Event(Navigator.NavigateTo(fragment))
    }

    fun showBottomSheetDialogFragment(fragment: BottomSheetDialogFragment, fragmentTag: String) {
        _navigationToLiveData.value =
            Event(Navigator.ShowBottomSheetDialogFragment(fragment, fragmentTag))
    }

    fun navigateBack() {
        _navigationToLiveData.value = Event(Navigator.NavigateBack)
    }
}