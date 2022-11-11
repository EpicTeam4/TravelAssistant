package com.example.travelassistant.features.favourites.domain.repository

import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Sights

/**
 * Репозиторий для списка избранных достопримечательностей
 *
 * @author Marianne Sabanchieva
 */

interface SightsRepository {
    suspend fun getFavouriteSights(): List<Sights>
    suspend fun getSightsById(id: Int): Sights?
    suspend fun getCities(): List<City>
    suspend fun deleteSightsFromFavourite(id: Int)
}