package com.example.travelassistant.core.network.model

import com.example.travelassistant.features.cities.domain.model.CityDomain

data class Location(val slug: String, val name: String) {

    fun toDomain(): CityDomain {
        return CityDomain(id = this.slug, name = this.name, imageUrl = null)
    }

}