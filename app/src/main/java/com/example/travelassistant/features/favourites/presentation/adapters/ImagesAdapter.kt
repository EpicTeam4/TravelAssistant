package com.example.travelassistant.features.favourites.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.databinding.FragmentPlaceDetailsRecyclerViewItemBinding
import com.squareup.picasso.Picasso

/**
 * Favourite places - адаптер для списка изображений
 *
 * @property imageURLs - список ссылок
 *
 * @author Marianne Sabanchieva
 */

class ImagesAdapter(private val imageURLs: MutableList<String>) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentPlaceDetailsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FragmentPlaceDetailsRecyclerViewItemBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_place_details_recycler_view_item, parent, false)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(imageURLs[position]) {
                Picasso.get()
                    .load(this)
                    .into(placeDetailsImage)
            }
        }
    }

    override fun getItemCount() = imageURLs.size

    fun setImages(images: List<String>) {
        imageURLs.clear()
        imageURLs.addAll(images.toMutableList())
        notifyDataSetChanged()
    }
}