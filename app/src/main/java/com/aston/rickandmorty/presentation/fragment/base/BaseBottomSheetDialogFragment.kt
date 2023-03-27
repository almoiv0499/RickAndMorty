package com.aston.rickandmorty.presentation.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val BINDING_EXCEPTION_MASSAGE_FOR_FILTER_FRAGMENT = "Binding cannot be null"

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding>(
    @LayoutRes private val layoutRes: Int,
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
) : BottomSheetDialogFragment(layoutRes) {


    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw java.lang.IllegalArgumentException(
            BINDING_EXCEPTION_MASSAGE_FOR_FILTER_FRAGMENT
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    protected abstract fun setUI()

}