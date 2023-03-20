package com.aston.rickandmorty.presentation.util

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

sealed class Navigator {
    data class NavigateTo(val fragment: Fragment) : Navigator()
    data class ShowBottomSheetDialogFragment(
        val fragment: BottomSheetDialogFragment,
        val fragmentTag: String,
    ) : Navigator()
    object NavigateBack : Navigator()
}