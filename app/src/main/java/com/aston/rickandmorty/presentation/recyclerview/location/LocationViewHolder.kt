package com.aston.rickandmorty.presentation.recyclerview.location

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aston.rickandmorty.databinding.LocationItemBinding
import com.aston.rickandmorty.presentation.model.location.LocationInfoView

class LocationViewHolder(
    private val binding: LocationItemBinding,
) : ViewHolder(binding.root) {

    fun populate(location: LocationInfoView, onLocationClickListener: OnLocationClickListener?) {
        with(binding) {
            locationName.text = location.name
            locationType.text = location.type
            locationDimension.text = location.dimension

            locationCardView.setOnClickListener {
                onLocationClickListener?.invoke(location)
            }
        }
    }

}