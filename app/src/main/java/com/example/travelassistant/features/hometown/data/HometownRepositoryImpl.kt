package com.example.travelassistant.features.hometown.data

import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.hometown.domain.repository.HometownRepository

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class HometownRepositoryImpl(private val dataSource: LocalDataSource) : HometownRepository {
    override suspend fun getCities(): List<City> = dataSource.getCities()
}