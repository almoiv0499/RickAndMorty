package com.aston.rickandmorty.presentation.fragment.characters

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharactersBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.recyclerview.characters.CharacterAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val EMPTY_VALUE = ""
private const val SPAN_COUNT = 2
private const val CHARACTER_NAME = "character name"
private const val CHARACTER_GENDER = "character gender"
private const val CHARACTER_STATUS = "character status"
private const val CHARACTER_SPECIES = "character species"

class CharactersFragment : BaseViewModelFragment<FragmentCharactersBinding, CharactersViewModel>(
    R.layout.fragment_characters,
    FragmentCharactersBinding::inflate,
    CharactersViewModel::class.java
), TitleToolbar {

    companion object {
        fun newInstance(): CharactersFragment = CharactersFragment()

        fun newInstance(
            characterName: String,
            characterStatus: String,
            characterSpecies: String,
            characterGender: String,
        ): CharactersFragment {

            val fragment = CharactersFragment()
            fragment.arguments = Bundle().apply {
                putString(CHARACTER_NAME, characterName)
                putString(CHARACTER_GENDER, characterGender)
                putString(CHARACTER_STATUS, characterStatus)
                putString(CHARACTER_SPECIES, characterSpecies)
            }
            return fragment
        }
    }

    private val characterAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterAdapter().apply {
            onCharacterClickListener = { character ->
                viewModel.launchCharacterDetailsFragment(character)
            }
        }
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        setupRecyclerView()

        binding.characterFilter.setOnClickListener {
            viewModel.launchDialogFragment()
        }
    }

    override fun setupObservers() {
        super.setupObservers()

        observeCharactersFlow()
    }

    override fun setToolbarTitle(): Int = R.string.characters_screen_name

    private fun setupRecyclerView() {
        with(binding.characterRecyclerView) {
            adapter = characterAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    private fun observeCharactersFlow() {
        val characterName = fetchFilterData(CHARACTER_NAME)
        val characterSpecies = fetchFilterData(CHARACTER_SPECIES)
        val characterGender = fetchFilterData(CHARACTER_GENDER)
        val characterStatus = fetchFilterData(CHARACTER_STATUS)

        lifecycleScope.launch {
            viewModel.charactersFlow(
                characterName, characterStatus, characterSpecies, characterGender
            ).collectLatest { pagingData ->
                characterAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    private fun fetchFilterData(key: String): String {
        return arguments?.getString(key) ?: EMPTY_VALUE
    }
}