package com.aston.rickandmorty.presentation.fragment.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.rickandmorty.presentation.util.Event
import com.aston.rickandmorty.presentation.util.NavigationManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseViewModel(
    private val context: Context,
) : ViewModel() {

    private val _manageFragmentLiveData = MutableLiveData<Event<NavigationManager>>()
    val manageFragmentLiveData: LiveData<Event<NavigationManager>> = _manageFragmentLiveData

    private val _exceptionLiveData = MutableLiveData<Int>()
    val exceptionLiveData: LiveData<Int> = _exceptionLiveData

    private val _internetConnectionLiveData = MutableLiveData<Boolean>()
    val internetConnectionLiveData: LiveData<Boolean> = _internetConnectionLiveData

    init {
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        _internetConnectionLiveData.value = hasInternetConnection()
    }

    fun launchFragment(fragment: Fragment) {
        _manageFragmentLiveData.value = Event(NavigationManager.LaunchFragment(fragment))
    }

    fun launchDialogFragment(fragment: BottomSheetDialogFragment, fragmentTag: String) {
        _manageFragmentLiveData.value =
            Event(NavigationManager.LaunchDialogFragment(fragment, fragmentTag))
    }

    fun refreshFragment(fragment: Fragment) {
        _manageFragmentLiveData.value = Event(NavigationManager.RefreshFragment(fragment))
    }

    fun showExceptionMessage(@StringRes exceptionMessage: Int) {
        _exceptionLiveData.value = exceptionMessage
    }

    fun hasInternetConnection(): Boolean {
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
}