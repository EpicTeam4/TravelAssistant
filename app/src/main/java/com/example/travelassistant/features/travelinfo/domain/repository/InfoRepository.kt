package com.example.travelassistant.features.travelinfo.domain.repository

import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.City

/**
 * Репозиторий для списка предустановленных городов, аэропортов и их детализации
 *
 * @author Marianne Sabanchieva
 */

interface InfoRepository {
    suspend fun getCities(): List<City>
    suspend fun getCityById(id: Long): City?

    suspend fun getPorts(): List<Port>
    suspend fun getPortById(id: Long): Port?
}