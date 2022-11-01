package com.example.travelassistant.core.domain

import com.example.travelassistant.core.database.dao.CityDao
import com.example.travelassistant.core.database.dao.PersonalItemDao
import com.example.travelassistant.core.database.dao.PortDao
import com.example.travelassistant.core.database.dao.TravelInfoDao
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.PersonalItem
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val city: CityDao,
    private val airport: PortDao,
    private val item: PersonalItemDao,
    private val details: TravelInfoDao
) {
    suspend fun getCities(): List<City> = city.getAllCities()
    suspend fun getCityById(id: Long): City? = city.getCityById(id)

    suspend fun getPorts(): List<Port> = airport.getAllPorts()

    suspend fun getAllItems(): List<PersonalItem> = item.getAllItems()
    suspend fun insertItem(personalItem: PersonalItem) = item.insertItem(personalItem)
    suspend fun deleteItem(id: Int) = item.deleteItem(id)

    suspend fun getDetails(date: Long): InfoAboutTravel? = details.getInfo(date)
    suspend fun insertDetails(info: InfoAboutTravel) = details.insertInfo(info)
    suspend fun updateDetails(info: InfoAboutTravel) = details.updateDetails(info)
}