package com.example.travelassistant.features.cities.data

import com.example.travelassistant.features.cities.data.model.CityData
import com.example.travelassistant.core.network.KudagoClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesRepository(private val kudagoClient: KudagoClient) {

    suspend fun getCities(): List<CityData> {
        return withContext(Dispatchers.IO) {
            kudagoClient.getLocations().map { CityData(it.slug, it.name) }
        }
    }

}