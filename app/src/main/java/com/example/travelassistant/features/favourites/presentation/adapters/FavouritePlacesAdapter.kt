package com.example.travelassistant.features.favourites.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.domain.entity.Sights
import com.example.travelassistant.databinding.FragmentPlacesRecyclerViewItemBinding
import com.squareup.picasso.Picasso

/**
 * Favourite places - адаптер для списка избранных достопримечательностей
 *
 * @property sights - список достопримечательностей
 * @property onItemClicked - обработка нажатия на элемент списка
 *
 * @author Marianne Sabanchieva
 */

class FavouritePlacesAdapter(
    private val sights: MutableList<Sights>,
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<FavouritePlacesAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentPlacesRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FragmentPlacesRecyclerViewItemBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_places_recycler_view_item, parent, false)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(sights[position]) {
                placeTitle.text = name
                placeDescription.text = description
                if (image?.isNotEmpty() == true) {
                    Picasso.get()
                        .load(image)
                        .into(placeImage)
                }
                imgFavorite.setImageResource(R.drawable.star_filled)
                root.setOnClickListener {
                    onItemClicked(id)
                }
            }
        }
    }

    override fun getItemCount() = sights.size

}