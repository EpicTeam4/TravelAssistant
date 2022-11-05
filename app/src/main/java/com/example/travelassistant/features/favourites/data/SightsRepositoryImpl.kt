package com.example.travelassistant.features.favourites.data

import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Sights
import com.example.travelassistant.features.favourites.domain.repository.SightsRepository

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class SightsRepositoryImpl(
    private val dataSource: LocalDataSource
) : SightsRepository {

    override suspend fun getFavouriteSights(): List<Sights> =
        dataSource.getFavouriteSights()

    override suspend fun getSightsById(id: Int): Sights? = dataSource.getSightsById(id)

    override suspend fun getCities(): List<City> = dataSource.getCities()

}