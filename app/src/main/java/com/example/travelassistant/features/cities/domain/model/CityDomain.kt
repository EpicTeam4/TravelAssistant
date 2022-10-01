package com.example.travelassistant.features.cities.domain.model

data class CityDomain(val id: String, val name: String, val imageUrl: String?) {

    fun withImageUrl(imageUrl: String): CityDomain {
        return CityDomain(this.id, this.name, imageUrl)
    }

}
