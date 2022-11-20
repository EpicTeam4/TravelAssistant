package com.example.travelassistant.features.favourites.domain.usecase

import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.usecase.safeCall
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
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

    suspend fun getFavouriteSights(location: String): State<List<PlaceDomain>> =
        withContext(Dispatchers.IO) {
            safeCall {
                repository.getFavouriteSights(location)
            }
        }

    suspend fun getSightsById(placeId: String): PlaceDomain =
        withContext(Dispatchers.IO) { repository.getSightsById(placeId) }

    suspend fun getCities(): State<List<City>> =
        withContext(Dispatchers.IO) {
            safeCall {
                repository.getCities().map {
                    it.copy(image = ASSETS_FOLDER.plus("${it.image}.jpg"))
                }
            }
        }

    suspend fun deleteSightsFromFavourite(place: PlaceDomain) =
        withContext(Dispatchers.IO) { repository.deleteSightFromFavourites(place) }

    companion object {
        private const val ASSETS_FOLDER = "file:///android_asset/"
    }
}