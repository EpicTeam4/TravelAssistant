package com.example.travelassistant.features.cities.domain.repository

import com.example.travelassistant.features.cities.domain.model.PlaceDomain

interface PlacesRepository {

    suspend fun getPlaces(location: String): Result<List<PlaceDomain>>

}