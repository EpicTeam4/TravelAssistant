package com.example.travelassistant.features.editinfo.domain.repository

import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.InfoAboutTravel

/**
 * Репозиторий для списка предустановленных городов, аэропортов и их детализации
 *
 * @author Marianne Sabanchieva
 */

interface InfoEditingRepository {

    suspend fun getCityById(id: Long): City?
    suspend fun getPorts(): List<Port>

    suspend fun getHotels(location: String): List<Hotel>

    suspend fun getDetails(date: Long): InfoAboutTravel?
    suspend fun updateDetails(info: InfoAboutTravel)

}