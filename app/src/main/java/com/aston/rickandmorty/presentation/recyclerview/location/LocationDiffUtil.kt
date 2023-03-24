package com.aston.rickandmorty.presentation.recyclerview.location

import androidx.recyclerview.widget.DiffUtil
import com.aston.rickandmorty.presentation.model.location.LocationInfoView

class LocationDiffUtil : DiffUtil.ItemCallback<LocationInfoView>() {

    override fun areItemsTheSame(oldItem: LocationInfoView, newItem: LocationInfoView): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LocationInfoView, newItem: LocationInfoView): Boolean =
        oldItem == newItem

}