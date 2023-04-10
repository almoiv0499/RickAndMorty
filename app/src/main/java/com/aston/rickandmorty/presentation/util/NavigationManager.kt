package com.aston.rickandmorty.presentation.util

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

sealed class NavigationManager {
    data class LaunchFragment(val fragment: Fragment) : NavigationManager()

    data class LaunchDialogFragment(
        val fragment: BottomSheetDialogFragment,
        val fragmentTag: String,
    ) : NavigationManager()

    data class RefreshFragment(val fragment: Fragment) : NavigationManager()

}