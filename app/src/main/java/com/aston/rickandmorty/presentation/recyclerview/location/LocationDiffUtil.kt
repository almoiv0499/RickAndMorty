package com.aston.rickandmorty.presentation.recyclerview.location

import androidx.recyclerview.widget.DiffUtil
import com.aston.rickandmorty.presentation.model.location.LocationInfoViewModel

class LocationDiffUtil : DiffUtil.ItemCallback<LocationInfoViewModel>() {

    override fun areItemsTheSame(oldItem: LocationInfoViewModel, newItem: LocationInfoViewModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LocationInfoViewModel, newItem: LocationInfoViewModel): Boolean =
        oldItem == newItem

}