package com.example.travelassistant.core.network

import com.example.travelassistant.core.data.toHotel
import com.example.travelassistant.core.domain.entity.Hotel
import javax.inject.Inject

/**
 * Hotel mapper - модель слоя Data в модель Domain
 *
 * @author Marianne Sabanchieva
 */

class ApiMapperHotel @Inject constructor(private val clientApi: KudagoClientApi) {
    suspend fun getHotels(location: String): List<Hotel> =
        clientApi.getHotels(location).results.map { it.toHotel() }
}