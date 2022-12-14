package com.example.travelassistant.features.cities.data

import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.features.cities.domain.model.CityDomain
import com.example.travelassistant.features.cities.domain.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesRepositoryNetworkImpl(private val kudagoClient: KudagoClient) : CitiesRepository {

    override suspend fun getCities(): List<CityDomain> {
        return withContext(Dispatchers.IO) {
            kudagoClient.getLocations().map { it.toDomain()}
        }
    }

}