package com.example.travelassistant.features.cities.data

import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.cities.domain.model.CityDomain
import com.example.travelassistant.features.cities.domain.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesRepositoryLocalDbImpl(private val dataSource: LocalDataSource) : CitiesRepository {

    override suspend fun getCities(): List<CityDomain> {
        return withContext(Dispatchers.IO) {
            dataSource.getCities().map { mapCityDbToCityDomain(it) }
        }
    }

    fun mapCityDbToCityDomain(cityDb: City): CityDomain {
        return CityDomain(cityDb.slug.orEmpty(), cityDb.name, null)
    }

}