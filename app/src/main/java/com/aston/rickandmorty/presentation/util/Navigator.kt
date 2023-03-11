package com.aston.rickandmorty.presentation.util

import androidx.fragment.app.Fragment

sealed class Navigator {
    data class NavigateTo(val fragment: Fragment): Navigator()
    object NavigateBack : Navigator()
}