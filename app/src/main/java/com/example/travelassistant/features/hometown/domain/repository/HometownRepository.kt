package com.example.travelassistant.features.hometown.domain.repository

import com.example.travelassistant.core.domain.entity.City

/**
 * Репозиторий для списка городов
 *
 * @author Marianne Sabanchieva
 */

interface HometownRepository {
    suspend fun getCities(): List<City>
}