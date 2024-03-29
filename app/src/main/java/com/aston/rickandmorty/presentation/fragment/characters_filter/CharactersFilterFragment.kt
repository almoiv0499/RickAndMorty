package com.aston.rickandmorty.presentation.fragment.characters_filter

import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentCharactersFilterBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseBottomSheetDialogViewModelFragment

private const val EMPTY_VALUE = ""

class CharactersFilterFragment :
    BaseBottomSheetDialogViewModelFragment<FragmentCharactersFilterBinding, CharactersFilterViewModel>(
        R.layout.fragment_characters_filter,
        FragmentCharactersFilterBinding::inflate,
        CharactersFilterViewModel::class.java
    ) {

    companion object {
        fun newInstance() = CharactersFilterFragment()
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        fetchData()
    }

    private fun fetchData() {
        with(binding) {
            characterFilterConfirm.setOnClickListener {
                val characterName = getDataFromEditText(characterFilterName.editText)
                val characterSpecies = getDataFromEditText(
                    characterFilterSpecies.editText
                )

                val characterStatus = getDataFromRadioGroup(characterFilterStatus)
                val characterGender = getDataFromRadioGroup(characterFilterGender)

                passDataAndDismissFragment(
                    characterName, characterStatus, characterSpecies, characterGender
                )
            }
            characterFilterClear.setOnClickListener {
                passDataAndDismissFragment(EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE)
            }
        }
    }

    private fun passDataAndDismissFragment(
        characterName: String, characterStatus: String,
        characterSpecies: String, characterGender: String,
    ) {
        viewModel.passFilteredData(
            characterName, characterStatus, characterSpecies, characterGender
        )
        dismiss()
    }

    private fun getDataFromEditText(editText: EditText?): String {
        return editText?.text?.toString()?.lowercase()?.trim() ?: EMPTY_VALUE
    }

    private fun getDataFromRadioGroup(radioGroup: RadioGroup): String {
        val radioButtonId = radioGroup.checkedRadioButtonId
        return radioGroup.findViewById<RadioButton>(radioButtonId).text.toString().trim()
            .lowercase()
    }

}