package com.example.travelassistant.features.travelinfo.domain.usecase

import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Get info use case
 *
 * @author Marianne Sabanchieva
 */

class GetInfoUseCase @Inject constructor(private val infoRepository: InfoRepository) {

    suspend fun getCities(): List<City> =
        withContext(Dispatchers.IO) {
            infoRepository.getCities().map {
                it.copy(image = ASSETS_FOLDER.plus("${it.image}.jpg"))
            }
        }

    suspend fun getCityById(id: Long): City? =
        withContext(Dispatchers.IO) { infoRepository.getCityById(id) }

    suspend fun getPorts(): List<Port> =
        withContext(Dispatchers.IO) { infoRepository.getPorts() }

    suspend fun getPortById(id: Long): Port? =
        withContext(Dispatchers.IO) { infoRepository.getPortById(id) }

    companion object {
        const val ASSETS_FOLDER = "file:///android_asset/"
    }
}