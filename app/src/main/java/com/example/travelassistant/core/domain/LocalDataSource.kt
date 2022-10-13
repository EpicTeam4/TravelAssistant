package com.example.travelassistant.core.domain

import com.example.travelassistant.core.database.dao.CityDao
import com.example.travelassistant.core.database.dao.PersonalItemDao
import com.example.travelassistant.core.database.dao.PortDao
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.PersonalItem
import com.example.travelassistant.core.domain.entity.Port
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val city: CityDao,
    private val airport: PortDao,
    private val item: PersonalItemDao
) {
    suspend fun getCities(): List<City> = city.getAllCities()
    suspend fun getCityById(id: Long): City? = city.getCityById(id)

    suspend fun getPorts(): List<Port> = airport.getAllPorts()
    suspend fun getPortById(id: Long): Port? = airport.getPortById(id)

    suspend fun getAllItems(): List<PersonalItem> = item.getAllItems()
}