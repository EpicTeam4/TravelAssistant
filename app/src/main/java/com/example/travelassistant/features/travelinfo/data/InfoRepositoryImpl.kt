package com.example.travelassistant.features.travelinfo.data

import com.example.travelassistant.core.database.dao.PortDAO
import com.example.travelassistant.core.database.dao.CityDAO
import com.example.travelassistant.features.travelinfo.domain.entity.Port
import com.example.travelassistant.features.travelinfo.domain.entity.City
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import javax.inject.Inject

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class InfoRepositoryImpl @Inject constructor(
    private val city: CityDAO,
    private val airport: PortDAO
) : InfoRepository {
    override fun getCities(): List<City> = city.getAllCities()
    override fun getCityById(id: Long): City = city.getCityById(id)

    override fun getPorts(): List<Port> = airport.getAllPorts()
    override fun getPortById(id: Long): Port = airport.getPortById(id)
}