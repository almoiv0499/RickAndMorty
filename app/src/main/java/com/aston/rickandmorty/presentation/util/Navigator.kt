package com.aston.rickandmorty.presentation.util

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

sealed class Navigator {
    data class LaunchFragment(val fragment: Fragment) : Navigator()

    data class LaunchDialogFragment(
        val fragment: BottomSheetDialogFragment,
        val fragmentTag: String,
    ) : Navigator()

    object NavigateBack : Navigator()
}