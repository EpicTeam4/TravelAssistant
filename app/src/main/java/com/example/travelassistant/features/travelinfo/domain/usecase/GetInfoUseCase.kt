package com.example.travelassistant.features.travelinfo.domain.usecase

import com.example.travelassistant.features.travelinfo.domain.entity.Port
import com.example.travelassistant.features.travelinfo.domain.entity.City
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import javax.inject.Inject

/**
 * Get info use case
 *
 * @author Marianne Sabanchieva
 */

interface GetInfoUseCase {
    fun getCities(): List<City>
    fun getCityById(id: Long): City

    fun getPorts(): List<Port>
    fun getPortById(id: Long): Port
}

class GetInfoUseCaseImpl @Inject constructor(private val infoRepository: InfoRepository): GetInfoUseCase {
    override fun getCities(): List<City> = infoRepository.getCities()
    override fun getCityById(id: Long): City = infoRepository.getCityById(id)

    override fun getPorts(): List<Port> = infoRepository.getPorts()
    override fun getPortById(id: Long): Port = infoRepository.getPortById(id)
}