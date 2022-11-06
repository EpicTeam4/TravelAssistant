package com.example.travelassistant.features.luggage.domain.usecase

import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.PersonalItem
import com.example.travelassistant.core.domain.usecase.safeCall
import com.example.travelassistant.features.luggage.domain.repository.LuggageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Get luggage use case
 *
 * @author Marianne Sabanchieva
 */

class GetLuggageUseCase @Inject constructor(private val luggageRepository: LuggageRepository) {

    suspend fun getAllItems(): State<List<PersonalItem>> =
        withContext(Dispatchers.IO) { safeCall { luggageRepository.getAllItems() } }

    suspend fun addItem(item: PersonalItem) =
        withContext(Dispatchers.IO) { luggageRepository.addItem(item) }

    suspend fun deleteItem(id: Int) =
        withContext(Dispatchers.IO) { luggageRepository.deleteItem(id) }

}