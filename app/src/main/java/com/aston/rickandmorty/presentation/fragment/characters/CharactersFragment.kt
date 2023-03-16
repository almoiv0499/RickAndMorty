package com.aston.rickandmorty.presentation.fragment.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharactersBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseFragment
import com.aston.rickandmorty.presentation.fragment.view_model_factory.FactoryForViewModels
import com.aston.rickandmorty.presentation.recyclerview.characters.CharacterAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar
import javax.inject.Inject

class CharactersFragment : BaseFragment<CharactersViewModel>(), TitleToolbar {

    companion object {
        fun newInstance(): CharactersFragment = CharactersFragment()
    }

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val characterAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterAdapter().apply {
            onCharacterClickListener = { character ->
                viewModel.navigateToCharacterDetailsFragment(character)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: FactoryForViewModels

    override val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]
    }

    private val component by lazy(LazyThreadSafetyMode.NONE) {
        (activity?.applicationContext as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
        observe()
    }

    private fun setupRecyclerView() {
        val mLayoutManager = GridLayoutManager(context, 2)
        with(binding.characterRecyclerView) {
            adapter = characterAdapter
            layoutManager = mLayoutManager
        }
    }

    private fun observe() {
            viewModel.charactersLiveData().observe(viewLifecycleOwner) { pagingData ->
                characterAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setToolbarTitle(): Int = R.string.characters_screen_name
}