package com.example.travelassistant.features.cities.domain.repository

import com.example.travelassistant.features.cities.domain.model.CityDomain

interface CitiesRepository {

    suspend fun getCities(): List<CityDomain>

}