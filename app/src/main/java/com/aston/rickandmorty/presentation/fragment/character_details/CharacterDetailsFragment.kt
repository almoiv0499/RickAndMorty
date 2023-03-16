package com.aston.rickandmorty.presentation.fragment.character_details

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.aston.rickandmorty.presentation.fragment.view_model_factory.FactoryForViewModels
import com.aston.rickandmorty.presentation.model.character.CharacterView
import com.aston.rickandmorty.presentation.recyclerview.episode.EpisodeAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbarDetails
import com.bumptech.glide.Glide
import javax.inject.Inject

class CharacterDetailsFragment : Fragment(), TitleToolbarDetails {

    companion object {
        private const val CHARACTER_ARGS_KEY = "character_args_key"

        fun newInstance(character: CharacterView): CharacterDetailsFragment {
            val fragment = CharacterDetailsFragment()
            val args = Bundle().apply {
                putParcelable(CHARACTER_ARGS_KEY, character)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: FactoryForViewModels

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory)[CharacterDetailsViewModel::class.java]
    }

    private val component by lazy(LazyThreadSafetyMode.NONE) {
        (activity?.applicationContext as App).component
    }

    private val episodeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        EpisodeAdapter()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setupArguments()
        observeEpisodes()
    }

    private fun observeEpisodes() {
        val character = getCharacterArguments()
        if (character != null) {
            viewModel.episodeLiveData(character.episode).observe(viewLifecycleOwner) { episodes ->
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

    private fun getCharacterArguments(): CharacterView? {
        return arguments?.parcelable(CHARACTER_ARGS_KEY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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