package com.example.travelassistant.features.cities.domain.repository

import com.example.travelassistant.features.cities.domain.model.PlaceDomain

interface PlacesRepository {

    suspend fun getPlaces(location: String): List<PlaceDomain>
    suspend fun getPlace(placeId: String): PlaceDomain
    suspend fun addPlaceToFavorites(place: PlaceDomain)
    suspend fun deletePlaceFromFavorites(place: PlaceDomain)

}