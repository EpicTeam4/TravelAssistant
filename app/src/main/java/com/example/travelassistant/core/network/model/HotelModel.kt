package com.example.travelassistant.core.network.model

import com.example.travelassistant.core.Constants.EMPTY_STRING
import com.google.gson.annotations.SerializedName

/**
 * Hotel model - модель для получения данных об отелях
 *
 * @property id - идентификатор
 * @property title - наименование отеля
 * @property address - адрес
 * @property phone - телефон
 * @property subway - станция (-ии) метро рядом
 * @property location - текстовый идентификатор города
 *
 * @author Marianne Sabanchieva
 */

data class HotelModel(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = EMPTY_STRING,
    @SerializedName("address")
    val address: String? = EMPTY_STRING,
    @SerializedName("phone")
    val phone: String? = EMPTY_STRING,
    @SerializedName("subway")
    val subway: String? = EMPTY_STRING,
    @SerializedName("location")
    val location: String? = EMPTY_STRING
)