package com.example.travelassistant.features.cities.domain

import com.example.travelassistant.features.cities.domain.model.CityDomain
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.example.travelassistant.features.cities.domain.repository.CitiesRepository
import com.example.travelassistant.features.cities.domain.repository.PlacesRepository

class CitiesUseCase(
    private val citiesRepository: CitiesRepository,
    private val placesRepository: PlacesRepository
) {

    suspend fun getCities(): Result<List<CityDomain>> {
        return safeCall {
            citiesRepository.getCities()
                .map { it.withImageUrl(makeUrl(it.id)) }
        }
    }

    suspend fun getPlaces(location: String): Result<List<PlaceDomain>> {
        return safeCall { placesRepository.getPlaces(location) }
    }

    suspend fun getPlace(placeId: String): Result<PlaceDomain> {
        return safeCall { placesRepository.getPlace(placeId) }
    }

    private fun makeUrl(cityId: String): String {
        val imageResource = cityImageResourceMap[cityId]
        return "file:///android_asset/" + (imageResource ?: "default-city.jpg")
    }

    companion object {
        val cityImageResourceMap: Map<String, String> = mapOf(
            Pair("ekb", "ekaterinburg.jpg"),
            Pair("krasnoyarsk", "krasnoyarsk.jpg"),
            Pair("krd", "krasnodar.jpg"),
            Pair("kzn", "kazan.jpg"),
            Pair("msk", "moscow.jpg"),
            Pair("nnv", "nizhnynovgorod.jpg"),
            Pair("nsk", "novosibirsk.jpg"),
            Pair("sochi", "sochi.jpg"),
            Pair("spb", "saintpetersburg.jpg"),
        )
    }

}