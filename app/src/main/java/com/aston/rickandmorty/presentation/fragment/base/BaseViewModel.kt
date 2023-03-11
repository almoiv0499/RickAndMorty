package com.aston.rickandmorty.presentation.fragment.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.presentation.util.Event
import com.aston.rickandmorty.presentation.util.Navigator

abstract class BaseViewModel : ViewModel() {

    private val _navigationToLiveData = MutableLiveData<Event<Navigator>>()
    val navigationToLiveData: LiveData<Event<Navigator>> = _navigationToLiveData

    fun navigateTo(fragment: Fragment) {
        _navigationToLiveData.value = Event(Navigator.NavigateTo(fragment))
    }

    fun navigateBack() {
        _navigationToLiveData.value = Event(Navigator.NavigateBack)
    }
}