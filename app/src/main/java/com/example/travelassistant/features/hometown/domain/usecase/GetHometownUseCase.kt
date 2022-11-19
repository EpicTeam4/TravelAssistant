package com.example.travelassistant.features.hometown.domain.usecase

import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.usecase.safeCall
import com.example.travelassistant.features.hometown.domain.repository.HometownRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Get info use case
 *
 * @author Marianne Sabanchieva
 */

class GetHometownUseCase @Inject constructor(private val infoRepository: HometownRepository) {
    suspend fun getCities(): State<List<City>> =
        withContext(Dispatchers.IO) {
            safeCall {
                infoRepository.getCities()
            }
        }

    suspend fun getHometown(): Flow<Int> =
        withContext(Dispatchers.IO) {
            infoRepository.getHometown()
        }

    suspend fun rewriteHometown(cityId: Int) =
        withContext(Dispatchers.IO) {
            infoRepository.rewriteHometown(cityId)
        }
}