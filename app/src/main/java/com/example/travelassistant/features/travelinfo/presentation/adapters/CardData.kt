package com.example.travelassistant.features.travelinfo.presentation.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.travelassistant.R

/**
 * Card data - подгружает изображение и наименование города в карточку
 *
 * @author Marianne Sabanchieva
 */

class CardData(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cityImage: ImageView = itemView.findViewById(R.id.city)
    private val cityName: TextView = itemView.findViewById(R.id.cityName)
    fun setContent(name: String?, img: String?) {
        val image = ASSETS_FOLDER.plus("$img.jpg")
        cityImage.load(image) {
            crossfade(true)
            placeholder(R.drawable.abstract_city)
        }
        cityName.text = name
    }

    companion object{
        private const val ASSETS_FOLDER = "file:///android_asset/"
    }
}