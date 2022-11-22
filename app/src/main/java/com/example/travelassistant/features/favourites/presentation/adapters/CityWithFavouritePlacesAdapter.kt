package com.example.travelassistant.features.favourites.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.databinding.FragmentFavouritesItemBinding
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.squareup.picasso.Picasso

/**
 * City with favourite places adapter - родительский адаптер
 *
 * @property cities - список городов
 * @property sights - список достопримечательностей
 * @property onItemClicked - обработка нажатия на элемент списка
 *
 * @author Marianne Sabanchieva
 */

class CityWithFavouritePlacesAdapter(
    private val cities: MutableList<City>,
    private val sights: MutableList<PlaceDomain>,
    private val deleteSights: (id: PlaceDomain) -> Unit,
    private val onItemClicked: (id: String) -> Unit
) : RecyclerView.Adapter<CityWithFavouritePlacesAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentFavouritesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FragmentFavouritesItemBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_favourites_item, parent, false)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(cities[position]) {
                if (image?.isNotEmpty() == true) {
                    Picasso.get()
                        .load(image)
                        .into(img)
                }
                itemName.text = name
                repeat(sights.size) {
                    val childMembersAdapter =
                        FavouritePlacesAdapter(sights.filter { sight ->
                            sight.location == slug
                        }.toMutableList(), { deleteSights(it) }) { onItemClicked(it) }
                    favouriteSights.adapter = childMembersAdapter
                }
            }
        }
    }

    override fun getItemCount() = cities.size

    fun setCitiesAndSights(citiesList: List<City>, sightsList: List<PlaceDomain>) {
        if (citiesList.isNotEmpty()) {
            cities.clear()
            cities.addAll(citiesList)
            notifyDataSetChanged()
        }

        if (sightsList.isNotEmpty()) {
            sights.addAll(sightsList)
            notifyDataSetChanged()
        }
    }

}