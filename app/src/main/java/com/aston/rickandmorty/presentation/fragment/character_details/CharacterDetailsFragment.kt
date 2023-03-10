package com.aston.rickandmorty.presentation.fragment.character_details

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aston.rickandmorty.R
import com.aston.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.aston.rickandmorty.presentation.model.CharacterInfoView
import com.bumptech.glide.Glide

class CharacterDetailsFragment : Fragment() {

    companion object {
        private const val CHARACTER_ARGS_KEY = "character_args_key"

        fun newInstance(character: CharacterInfoView): CharacterDetailsFragment {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArguments()
    }

    private fun setupArguments() {
        val character = getCharacterArguments()
        if (character != null) {
            with(binding) {
                characterDetailsName.text = character.name
                characterDetailsStatus.text = character.status
                characterDetailsGender.text = character.gender
                characterDetailsSpecies.text = character.species

                Glide.with(this@CharacterDetailsFragment)
                    .load(character.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(characterDetailsImage)
            }
        }
    }

    private fun getCharacterArguments(): CharacterInfoView? {
        return arguments?.parcelable(CHARACTER_ARGS_KEY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}