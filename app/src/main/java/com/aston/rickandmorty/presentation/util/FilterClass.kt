package com.aston.rickandmorty.presentation.util

import android.widget.Filter
import com.aston.rickandmorty.presentation.model.character.CharacterInfoView
import com.aston.rickandmorty.presentation.recyclerview.characters.CharacterAdapter

// Позже доработать
class FilterClass {

    companion object {
        fun filter(
            filteredVacancies: List<CharacterInfoView>,
            adapter: CharacterAdapter,
        ): Filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val temp = mutableListOf<CharacterInfoView>()
                if (constraint == null || constraint.isEmpty()) {
                    temp.addAll(filteredVacancies)
                } else {
                    val pattern = constraint.toString().lowercase().trim()
                    for (item in filteredVacancies) {
                        if (isContains(item, pattern)) {
                            temp.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = temp
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                adapter.submitList(results?.values as List<CharacterView>)
            }
        }

        private fun isContains(item: CharacterInfoView, pattern: String): Boolean {
            return item.name.lowercase().contains(pattern)
                    || item.species.lowercase().contains(pattern)
                    || item.status.lowercase().contains(pattern)
                    || item.gender.lowercase().contains(pattern)
        }
    }
}