package com.aston.rickandmorty.presentation.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

private const val BINDING_EXCEPTION_MASSAGE = "Binding cannot be null"

abstract class BaseFragment<VB : ViewBinding>(
    @LayoutRes private val layoutRes: Int,
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
) : Fragment(layoutRes) {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw java.lang.IllegalArgumentException(BINDING_EXCEPTION_MASSAGE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(inflater)

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
    }

    protected abstract fun setUI()

    abstract fun setupRecyclerView()

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}