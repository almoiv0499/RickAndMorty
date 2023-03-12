package com.aston.rickandmorty.presentation.fragment.characters

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharactersBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseFragment
import com.aston.rickandmorty.presentation.fragment.filter.FilterCharacterFragment
import com.aston.rickandmorty.presentation.fragment.view_model_factory.FactoryForViewModels
import com.aston.rickandmorty.presentation.recyclerview.characters.CharacterAdapter
import com.aston.rickandmorty.presentation.util.TitleToolbar
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.properties.Delegates.notNull

class CharactersFragment : BaseFragment<CharactersViewModel>(), TitleToolbar {

    companion object {
        private const val KEY_STATUS = "key status"
        private const val STATUS_EMPTY_VALUE = "status empty value"

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
        observeViewModel()
        handleToolbarItem()
        setupDialogFragmentListener()
    }

    private fun handleToolbarItem() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.search -> {
                        val search = menuItem.actionView as SearchView
                        search.setOnQueryTextListener(object : OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                characterAdapter.filter.filter(newText)
                                return true
                            }
                        })
                    }
                    R.id.filter -> {
                        showDialogFragment()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeViewModel() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { characters ->
            characterAdapter.setFilteredCharacters(characters.characterInfo)
        }
    }

    private fun setupRecyclerView() {
        with(binding.characterRecyclerView) {
            adapter = characterAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogFragment() {
        activity?.let { activity ->
            FilterCharacterFragment.show(activity.supportFragmentManager, "")
        }
    }

    private fun setupDialogFragmentListener() {
        activity?.let { activity ->
            FilterCharacterFragment.setupListener(activity.supportFragmentManager, this) { status ->
                characterAdapter.filter.filter(status)
            }
        }
    }

    override fun setToolbarTitle(): Int = R.string.characters_screen_name
}