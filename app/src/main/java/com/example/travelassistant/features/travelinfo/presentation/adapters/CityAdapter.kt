package com.example.travelassistant.features.travelinfo.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.travelassistant.R
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.databinding.FragmentCityBinding

/**
 * City adapter - адаптер для списка предустановленных городов
 *
 * @property cities - список городов
 * @property onItemClicked - обработка нажатия на элемент списка
 *
 * @author Marianne Sabanchieva
 */

class CityAdapter(
    private val cities: MutableList<City>,
    private val onItemClicked: (id: Long) -> Unit
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentCityBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FragmentCityBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_city, parent, false)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(cities[position]) {
                cityName.text = name
                if (image?.isNotEmpty() == true) {
                    city.load(image) {
                        crossfade(true)
                        placeholder(R.drawable.abstract_city)
                    }
                }
                root.setOnClickListener {
                    onItemClicked(id)
                }
            }
        }
    }

    fun setCities(citiesList: List<City>) {
        if (citiesList.isNotEmpty()) {
            cities.clear()
            cities.addAll(citiesList)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = cities.size
}