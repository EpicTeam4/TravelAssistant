package com.example.travelassistant.features.cities.domain

import com.example.travelassistant.features.cities.domain.model.CityDomain
import com.example.travelassistant.features.cities.domain.repository.CitiesRepository

class CitiesUseCase(private val citiesRepository : CitiesRepository) {

    suspend fun getCities(): List<CityDomain> {
        return citiesRepository.getCities().map { CityDomain(it.id, it.name, findUrl(it.id)) } // todo wrong CityDomain map to CityDomain
    }

    private fun findUrl(cityId: String): String {
        val imageResource = cityImageResourceMap[cityId]
        val imageFileName = "file:///android_asset/" + (imageResource ?: "default-city.jpg")
        return imageFileName
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