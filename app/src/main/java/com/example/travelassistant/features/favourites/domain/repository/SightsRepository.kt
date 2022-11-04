package com.example.travelassistant.features.favourites.domain.repository

import com.example.travelassistant.core.domain.entity.FavouriteSights

/**
 * Репозиторий для списка избранных достопримечательностей
 *
 * @author Marianne Sabanchieva
 */

interface SightsRepository {
    suspend fun getFavouriteSights(): List<FavouriteSights>
    suspend fun getSightsById(id: Int): FavouriteSights?
}