package com.aston.rickandmorty.presentation.recyclerview.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.aston.rickandmorty.databinding.LocationItemBinding
import com.aston.rickandmorty.presentation.model.location.LocationInfoViewModel

typealias OnLocationClickListener = (LocationInfoViewModel) -> Unit

class LocationAdapter :
    PagingDataAdapter<LocationInfoViewModel, LocationViewHolder>(LocationDiffUtil()) {

    var onLocationClickListener: OnLocationClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = LocationItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        if (location != null) {
            holder.populate(location, onLocationClickListener)
        }
    }
}