package com.example.travelassistant.features.editinfo.domain.usecase

import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.domain.usecase.safeCall
import com.example.travelassistant.features.editinfo.domain.repository.InfoEditingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Get edit info use case
 *
 * @author Marianne Sabanchieva
 */

class GetEditInfoUseCase @Inject constructor(private val infoRepository: InfoEditingRepository) {

    suspend fun getCityById(id: Long): City? =
        withContext(Dispatchers.IO) { infoRepository.getCityById(id) }

    suspend fun getPorts(): State<List<Port>> =
        withContext(Dispatchers.IO) { safeCall { infoRepository.getPorts() } }

    suspend fun getHotels(location: String): State<List<Hotel>> =
        withContext(Dispatchers.IO) { safeCall { infoRepository.getHotels(location) } }

    suspend fun getDetails(date: Long): State<InfoAboutTravel?> =
        withContext(Dispatchers.IO) { safeCall { infoRepository.getDetails(date) } }

    suspend fun updateDetails(info: InfoAboutTravel) =
        withContext(Dispatchers.IO) { infoRepository.updateDetails(info) }

    suspend fun getHometown(): Flow<Int> =
        withContext(Dispatchers.IO) {
            infoRepository.getHometown()
        }

}