package com.example.travelassistant.features.travelinfo.data

import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class InfoRepositoryImpl(
    private val dataSource: LocalDataSource
) : InfoRepository {
    override suspend fun getCities(): List<City> = dataSource.getCities()
    override suspend fun getCityById(id: Long): City? = dataSource.getCityById(id)

    override suspend fun getPorts(): List<Port> = dataSource.getPorts()
    override suspend fun getPortById(id: Long): Port? = dataSource.getPortById(id)
}