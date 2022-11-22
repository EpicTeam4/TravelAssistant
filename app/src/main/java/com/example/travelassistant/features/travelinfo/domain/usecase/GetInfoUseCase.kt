package com.example.travelassistant.features.travelinfo.domain.usecase

import com.example.travelassistant.core.Constants.FOR_ANIMALS
import com.example.travelassistant.core.Constants.FOR_DOGS
import com.example.travelassistant.core.Constants.HOTEL_FOR_ANIMALS
import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.domain.usecase.safeCall
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Get info use case
 *
 * @author Marianne Sabanchieva
 */

class GetInfoUseCase @Inject constructor(private val infoRepository: InfoRepository) {

    suspend fun getCities(): State<List<City>> =
        withContext(Dispatchers.IO) {
            safeCall {
                infoRepository.getCities().map {
                    it.copy(image = ASSETS_FOLDER.plus("${it.image}.jpg"))
                }
            }
        }

    suspend fun getCityById(id: Long): City? =
        withContext(Dispatchers.IO) { infoRepository.getCityById(id) }

    suspend fun getPorts(): State<List<Port>> =
        withContext(Dispatchers.IO) { safeCall { infoRepository.getPorts() } }

    suspend fun getHotels(location: String): State<List<Hotel>> =
        withContext(Dispatchers.IO) {
            safeCall {
                infoRepository.getHotels(location).filter { hotel ->
                    !(hotel.title.contains(FOR_ANIMALS) && !hotel.title.contains(FOR_DOGS)
                            && !hotel.title.contains(HOTEL_FOR_ANIMALS))
                }
            }
        }

    suspend fun addDetails(info: InfoAboutTravel) =
        withContext(Dispatchers.IO) { infoRepository.addDetails(info) }

    suspend fun getHometown(): Flow<Int> =
        withContext(Dispatchers.IO) {
            infoRepository.getHometown()
        }

    companion object {
        private const val ASSETS_FOLDER = "file:///android_asset/"
    }

}