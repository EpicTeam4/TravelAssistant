package com.example.travelassistant.features.favourites.domain.usecase

import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Sights
import com.example.travelassistant.core.domain.usecase.safeCall
import com.example.travelassistant.features.favourites.domain.repository.SightsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Sights use case
 *
 * @author Marianne Sabanchieva
 */

class SightsUseCase @Inject constructor(private val repository: SightsRepository) {

    suspend fun getFavouriteSights(): State<List<Sights>> =
        withContext(Dispatchers.IO) {
            safeCall {
                repository.getFavouriteSights()
            }
        }

    suspend fun getSightsById(id: Int): Sights? =
        withContext(Dispatchers.IO) { repository.getSightsById(id) }

    suspend fun getCities(): State<List<City>> =
        withContext(Dispatchers.IO) {
            safeCall {
                repository.getCities()
            }
        }
}