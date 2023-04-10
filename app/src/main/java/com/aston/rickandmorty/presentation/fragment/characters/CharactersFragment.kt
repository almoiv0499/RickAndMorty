package com.aston.rickandmorty.presentation.fragment.characters

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharactersBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.recyclerview.characters.CharacterAdapter
import com.aston.rickandmorty.presentation.recyclerview.loader_state.LoaderStateFooterAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar

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
        setArguments()
        swipeToRefresh()
        launchFilterFragment()
        checkLoadState()
    }

    override fun setupObservers() {
        super.setupObservers()

        observeCharacters()
        observeInternetConnection()
    }

    override fun setupRecyclerView() {
        with(binding) {
            characterRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
            characterRecyclerView.adapter = characterAdapter.withLoadStateFooter(
                footer = LoaderStateFooterAdapter()
            )
        }
    }

    override fun setToolbarTitle(): Int = R.string.characters_screen_name

    private fun checkLoadState() {
        with(binding) {
            observeLoadState(
                adapter = characterAdapter,
                recyclerView = characterRecyclerView,
                filter = characterFilter,
                progress = charactersProgressBar,
                errorMessage = charactersErrorMessage
            )
        }
    }

    private fun observeCharacters() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { paging ->
            characterAdapter.submitData(viewLifecycleOwner.lifecycle, paging)
        }
    }

    private fun observeInternetConnection() {
        viewModel.internetConnectionLiveData.observe(viewLifecycleOwner) { hasInternetConnection ->
            binding.internetConnectionMessage.visibility =
                getVisibilityByInternetConnection(hasInternetConnection)
        }
    }

    private fun swipeToRefresh() {
        binding.charactersSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshFragment()
            binding.charactersSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun launchFilterFragment() {
        binding.characterFilter.setOnClickListener {
            viewModel.launchDialogFragment()
        }
    }

    private fun setArguments() {
        val characterName = fetchFilteredData(CHARACTER_NAME)
        val characterSpecies = fetchFilteredData(CHARACTER_SPECIES)
        val characterGender = fetchFilteredData(CHARACTER_GENDER)
        val characterStatus = fetchFilteredData(CHARACTER_STATUS)

        viewModel.getCharacters(
            characterName, characterStatus, characterSpecies, characterGender
        )
    }

    private fun fetchFilteredData(key: String): String {
        return arguments?.getString(key) ?: EMPTY_VALUE
    }
}