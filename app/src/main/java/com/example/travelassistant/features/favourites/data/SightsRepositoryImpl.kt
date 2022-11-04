package com.example.travelassistant.features.favourites.data

import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.FavouriteSights
import com.example.travelassistant.features.favourites.domain.repository.SightsRepository

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class SightsRepositoryImpl(
    private val dataSource: LocalDataSource
) : SightsRepository {

    override suspend fun getFavouriteSights(): List<FavouriteSights> =
        dataSource.getFavouriteSights()

    override suspend fun getSightsById(id: Int): FavouriteSights? = dataSource.getSightsById(id)

}