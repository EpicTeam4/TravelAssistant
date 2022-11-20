package com.example.travelassistant.features.favourites.domain.repository

import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.cities.domain.model.PlaceDomain

/**
 * Репозиторий для списка избранных достопримечательностей
 *
 * @author Marianne Sabanchieva
 */

interface SightsRepository {
    suspend fun getFavouriteSights(location: String): List<PlaceDomain>
    suspend fun getSightsById(placeId: String): PlaceDomain
    suspend fun getCities(): List<City>
    suspend fun deleteSightFromFavourites(place: PlaceDomain)
}