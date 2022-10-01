package com.example.travelassistant.features.cities.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.databinding.FragmentPlacesRecyclerViewItemBinding
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.squareup.picasso.Picasso

class PlacesRecyclerViewAdapter(private val places: MutableList<PlaceDomain>) :
    RecyclerView.Adapter<PlacesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentPlacesRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentPlacesRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(places[position]) {
                placeTitle.text = title
                placeDescription.text = shortDescription
                if (images.isNotEmpty()) {
                    images.first().let {
                        Picasso.get()
                            .load(it.url)
                            .into(placeImage)
                    }
                }
            }
        }
    }

    override fun getItemCount() = places.size

    fun setPlaces(places: List<PlaceDomain>) {
        if (places.isNotEmpty()) {
            this.places.clear()
            this.places.addAll(places)
            notifyDataSetChanged()
        }
    }

}