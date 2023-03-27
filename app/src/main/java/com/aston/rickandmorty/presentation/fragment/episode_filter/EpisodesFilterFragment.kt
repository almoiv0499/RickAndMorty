package com.aston.rickandmorty.presentation.fragment.episode_filter

import android.widget.EditText
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentEpisodesFilterBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseBottomSheetDialogViewModelFragment

private const val EMPTY_VALUE = ""

class EpisodesFilterFragment :
    BaseBottomSheetDialogViewModelFragment<FragmentEpisodesFilterBinding, EpisodesFilterViewModel>(
        R.layout.fragment_episodes_filter,
        FragmentEpisodesFilterBinding::inflate,
        EpisodesFilterViewModel::class.java
    ) {

    companion object {
        fun newInstance() = EpisodesFilterFragment()
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        fetchEnteredData()
    }

    private fun fetchEnteredData() {
        with(binding) {
            episodeFilterConfirm.setOnClickListener {
                val episodeName = getDataFromEditText(episodeFilterName.editText)
                val episodeNumber = getDataFromEditText(episodeFilterNumber.editText)

                observeAndDismissDialogFragment(episodeName, episodeNumber)
            }
            episodeFilterClear.setOnClickListener {
                observeAndDismissDialogFragment(EMPTY_VALUE, EMPTY_VALUE)
            }
        }
    }

    private fun observeAndDismissDialogFragment(episodeName: String, episodeNumber: String) {
        viewModel.launchFilteredFragment(episodeName, episodeNumber)
        dismiss()
    }

    private fun getDataFromEditText(editText: EditText?): String {
        return editText?.text?.toString()?.lowercase()?.trim() ?: EMPTY_VALUE
    }

}