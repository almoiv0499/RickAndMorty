package com.aston.rickandmorty.presentation.fragment.location_filter

import android.widget.EditText
import com.aston.rickandmorty.R
import com.aston.rickandmorty.app.App
import com.aston.rickandmorty.databinding.FragmentLocationsFilterBinding
import com.aston.rickandmorty.presentation.fragment.base.BaseBottomSheetDialogViewModelFragment

private const val EMPTY_VALUE = ""

class LocationsFilterFragment :
    BaseBottomSheetDialogViewModelFragment<FragmentLocationsFilterBinding, LocationFilterViewModel>(
        R.layout.fragment_locations_filter,
        FragmentLocationsFilterBinding::inflate,
        LocationFilterViewModel::class.java
    ) {

    companion object {
        fun newInstance() = LocationsFilterFragment()
    }

    override fun injectDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun setUI() {
        fetchEnteredData()
    }

    private fun fetchEnteredData() {
        with(binding) {
            locationFilterConfirm.setOnClickListener {
                val locationName = getDataFromEditText(locationFilterName.editText)
                val locationType = getDataFromEditText(locationFilterType.editText)
                val locationDimension = getDataFromEditText(locationFilterDimension.editText)

                observeAndDismissDialogFragment(locationName, locationType, locationDimension)
            }
            locationFilterClear.setOnClickListener {
                observeAndDismissDialogFragment(EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE)
            }
        }
    }

    private fun observeAndDismissDialogFragment(
        locationName: String, locationType: String, locationDimension: String,
    ) {
        viewModel.launchFilteredLocationsFragment(locationName, locationType, locationDimension)
        dismiss()
    }

    private fun getDataFromEditText(editText: EditText?): String {
        return editText?.text?.toString()?.lowercase()?.trim() ?: EMPTY_VALUE
    }
}