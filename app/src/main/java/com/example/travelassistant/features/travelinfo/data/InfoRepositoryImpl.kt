package com.example.travelassistant.features.travelinfo.data

import com.example.travelassistant.core.domain.DataStoreManager
import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.network.ApiMapperHotel
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import kotlinx.coroutines.flow.Flow

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class InfoRepositoryImpl(
    private val dataSource: LocalDataSource,
    private val apiMapper: ApiMapperHotel,
    private val prefs: DataStoreManager
) : InfoRepository {
    override suspend fun getCities(): List<City> = dataSource.getCities()
    override suspend fun getCityById(id: Long): City? = dataSource.getCityById(id)

    override suspend fun getPorts(): List<Port> = dataSource.getPorts()
    override suspend fun getHotels(location: String): List<Hotel> = apiMapper.getHotels(location)

    override suspend fun addDetails(info: InfoAboutTravel) = dataSource.insertDetails(info)
    override suspend fun getHometown(): Flow<Int> = prefs.getHometown()
}