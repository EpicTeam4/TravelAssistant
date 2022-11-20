package com.example.travelassistant.features.cities.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.databinding.FragmentPlaceDetailsRecyclerViewItemBinding
import com.squareup.picasso.Picasso

class PlaceDetailImagesRecyclerViewAdapter(
    private val urls: MutableList<String>,
) :
    RecyclerView.Adapter<PlaceDetailImagesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentPlaceDetailsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentPlaceDetailsRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(urls[position]) {
                Picasso.get()
                    .load(this)
                    .into(placeDetailsImage)
            }
        }
    }

    override fun getItemCount() = urls.size

    fun setUrls(_urls: List<String>) {
        urls.addAll(_urls.toMutableList())
        notifyDataSetChanged()
    }

}