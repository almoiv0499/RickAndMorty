package com.aston.rickandmorty.presentation.fragment.character_details

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseViewModelFragment
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import com.aston.rickandmorty.presentation.recyclerview.episode.EpisodeAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails
import com.bumptech.glide.Glide

private const val CHARACTER_ARGS_KEY = "character_args_key"

class CharacterDetailsFragment :
    BaseViewModelFragment<FragmentCharacterDetailsBinding, CharacterDetailsViewModel>(
        R.layout.fragment_character_details,
        FragmentCharacterDetailsBinding::inflate,
        CharacterDetailsViewModel::class.java
    ), TitleToolbarDetails {

    companion object {
        fun newInstance(character: CharacterInfoView): CharacterDetailsFragment {
            val fragment = CharacterDetailsFragment()
            val args = Bundle().apply {
                putParcelable(CHARACTER_ARGS_KEY, character)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val episodeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        EpisodeAdapter()
    }

    private fun getCharacterArguments(): CharacterInfoView? {
        return arguments?.parcelable(CHARACTER_ARGS_KEY)
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        initRecyclerView()
        setupArguments()
    }

    override fun setupObservers() {
        super.setupObservers()

        observeEpisodes()
    }

    private fun observeEpisodes() {
        val character = getCharacterArguments()
        if (character != null) {
            viewModel.episodeLiveData(character.episodes).observe(viewLifecycleOwner) { episodes ->
                episodeAdapter.submitList(episodes)
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.characterEpisodeRecyclerView) {
            adapter = episodeAdapter
        }
    }

    private fun setupArguments() {
        val character = getCharacterArguments()
        if (character != null) {
            with(binding) {
                characterDetailsName.text = character.name
                characterDetailsStatus.text = character.status
                characterDetailsGender.text = character.gender
                characterDetailsSpecies.text = character.species

                Glide.with(this@CharacterDetailsFragment).load(character.image)
                    .placeholder(R.drawable.ic_launcher_foreground).into(characterDetailsImage)
            }
        }
    }

    override fun setToolbarTitle(): String {
        val character = getCharacterArguments()
        return character?.name ?: getString(R.string.characters_screen_name)
    }
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}