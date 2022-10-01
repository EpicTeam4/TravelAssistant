package com.example.travelassistant.features.cities.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.databinding.FragmentCitiesRecyclerViewItemBinding
import com.example.travelassistant.features.cities.domain.model.CityDomain
import com.squareup.picasso.Picasso

class CitiesRecyclerViewAdapter(
    val cities: MutableList<CityDomain>,
    private val onItemClicked: (id: String) -> Unit
) :
    RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentCitiesRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentCitiesRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(cities[position]) {
                cityTitle.text = name
                Picasso.get()
                    .load(imageUrl)
                    .into(cityImage);
                root.setOnClickListener {
                    onItemClicked(id)
                }
            }
        }
    }

    override fun getItemCount() = cities.size

    fun setCities(citiesList: List<CityDomain>) {
        if (citiesList.isNotEmpty()) {
            cities.clear()
            cities.addAll(citiesList)
            notifyDataSetChanged()
        }
    }

}



