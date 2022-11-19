package com.example.travelassistant.features.hometown.domain.repository

import com.example.travelassistant.core.domain.entity.City
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для списка городов
 *
 * @author Marianne Sabanchieva
 */

interface HometownRepository {
    suspend fun getCities(): List<City>
    suspend fun getHometown(): Flow<Int>
    suspend fun rewriteHometown(cityId: Int)
}