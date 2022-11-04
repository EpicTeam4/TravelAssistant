package com.example.travelassistant.features.favourites.domain.usecase

import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.FavouriteSights
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

    suspend fun getFavouriteSights(): State<List<FavouriteSights>> =
        withContext(Dispatchers.IO) {
            safeCall {
                repository.getFavouriteSights()
            }
        }

    suspend fun getSightsById(id: Int): FavouriteSights? =
        withContext(Dispatchers.IO) { repository.getSightsById(id) }

}