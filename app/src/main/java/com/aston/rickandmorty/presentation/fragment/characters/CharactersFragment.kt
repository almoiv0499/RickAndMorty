package com.aston.rickandmorty.presentation.fragment.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.databinding.FragmentCharactersBinding
import com.aston.rickandmorty.presentation.recyclerview.characters.CharacterAdapter

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val characterAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(binding.characterRecyclerView) {
            adapter = characterAdapter
            layoutManager= GridLayoutManager(context, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}