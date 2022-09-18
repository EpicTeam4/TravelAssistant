package com.example.travelassistant.features.travelinfo.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.domain.entity.City

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
) : RecyclerView.Adapter<CardData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardData {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_city, parent, false)
        return CardData(view)
    }

    override fun onBindViewHolder(holder: CardData, position: Int) {
        with(cities[position]) {
            holder.setContent(name, image)
            holder.itemView.rootView.setOnClickListener {
                onItemClicked(id)
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