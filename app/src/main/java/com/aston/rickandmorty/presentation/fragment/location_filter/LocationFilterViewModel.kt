package com.aston.rickandmorty.presentation.fragment.location_filter

import com.aston.rickandmorty.presentation.fragment.base.BaseBottomSheetDialogViewModel
import com.aston.rickandmorty.presentation.fragment.locations.LocationsFragment
import javax.inject.Inject

class LocationFilterViewModel @Inject constructor() : BaseBottomSheetDialogViewModel() {

    fun launchFilteredFragment(
        locationName: String,
        locationType: String,
        locationDimension: String,
    ) {
        launchFilteredFragment(
            LocationsFragment.newInstance(
                locationName, locationType, locationDimension
            )
        )

    }

}