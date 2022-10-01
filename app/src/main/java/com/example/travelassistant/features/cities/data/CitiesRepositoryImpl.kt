package com.example.travelassistant.features.cities.data

import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.features.cities.domain.model.CityDomain
import com.example.travelassistant.features.cities.domain.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesRepositoryImpl(private val kudagoClient: KudagoClient) : CitiesRepository {

    override suspend fun getCities(): List<CityDomain> {
        return withContext(Dispatchers.IO) {
            kudagoClient.getLocations().map { CityDomain(id = it.slug, name = it.name, imageUrl = null) }
        }
    }

}