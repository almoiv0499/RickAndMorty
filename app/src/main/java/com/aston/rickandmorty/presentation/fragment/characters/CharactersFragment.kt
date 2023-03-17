package com.aston.rickandmorty.presentation.fragment.characters

import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharactersBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.recyclerview.characters.CharacterAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar

class CharactersFragment : BaseViewModelFragment<FragmentCharactersBinding, CharactersViewModel>(
    R.layout.fragment_characters,
    FragmentCharactersBinding::inflate,
    CharactersViewModel::class.java
), TitleToolbar {

    companion object {
        fun newInstance(): CharactersFragment = CharactersFragment()
    }

    private val characterAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterAdapter().apply {
            onCharacterClickListener = { character ->
                viewModel.navigateToCharacterDetailsFragment(character)
            }
        }
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        setupRecyclerView()
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.charactersLiveData().observe(viewLifecycleOwner) { pagingData ->
            characterAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
    }

    private fun setupRecyclerView() {
        with(binding.characterRecyclerView) {
            adapter = characterAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun setToolbarTitle(): Int = R.string.characters_screen_name
}