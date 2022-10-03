package com.example.travelassistant.core.domain.entity

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

data class Hotel(
    val id: Int,
    val title: String,
    val address: String,
    val phone: String,
    val subway: String,
    val location: String
)