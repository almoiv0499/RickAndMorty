package com.aston.rickandmorty.presentation.util

import android.widget.Filter
import com.aston.rickandmorty.presentation.model.character.CharacterView
import com.aston.rickandmorty.presentation.recyclerview.characters.CharacterAdapter

class FilterClass {

    companion object {
        fun filter(
            filteredVacancies: List<CharacterView>,
            adapter: CharacterAdapter,
        ): Filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val temp = mutableListOf<CharacterView>()
                if (constraint == null || constraint.isEmpty()) {
                    temp.addAll(filteredVacancies)
                } else {
                    val pattern = constraint.toString().lowercase().trim()
                    for (item in filteredVacancies) {
                        if (item.name.lowercase().contains(pattern)
                            || item.species.lowercase().contains(pattern)
                            || item.status.lowercase().contains(pattern)
                            || item.gender.lowercase().contains(pattern)
                        ) {
                            temp.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = temp
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                adapter.submitList(results?.values as List<CharacterView>)
            }
        }
    }
}