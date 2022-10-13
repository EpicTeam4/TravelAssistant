package com.example.travelassistant.core.data

import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.network.model.HotelModel

/**
 * To hotel - функция конвертирует модель слоя Data в модель Domain
 *
 * @author Marianne Sabanchieva
 */

fun HotelModel.toHotel() = Hotel(
    id = id,
    title = title,
    address = address.orEmpty(),
    phone = phone.orEmpty(),
    subway = subway.orEmpty(),
    location = location.orEmpty()
)