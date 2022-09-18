package com.example.travelassistant.features.travelinfo.domain.repository

import com.example.travelassistant.features.travelinfo.domain.entity.Port
import com.example.travelassistant.features.travelinfo.domain.entity.City

/**
 * Репозиторий для списка предустановленных городов, аэропортов и их детализации
 *
 * @author Marianne Sabanchieva
 */

interface InfoRepository {
    fun getCities(): List<City>
    fun getCityById(id: Long): City

    fun getPorts(): List<Port>
    fun getPortById(id: Long): Port
}