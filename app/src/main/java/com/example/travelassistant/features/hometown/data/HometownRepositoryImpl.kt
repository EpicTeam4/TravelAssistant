package com.example.travelassistant.features.hometown.data

import com.example.travelassistant.core.domain.DataStoreManager
import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.hometown.domain.repository.HometownRepository
import kotlinx.coroutines.flow.Flow

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class HometownRepositoryImpl(
    private val dataSource: LocalDataSource,
    private val prefs: DataStoreManager
) : HometownRepository {
    override suspend fun getCities(): List<City> = dataSource.getCities()
    override suspend fun getHometown(): Flow<Int> = prefs.getHometown()
    override suspend fun rewriteHometown(cityId: Int) = prefs.rewriteHometown(cityId)
}