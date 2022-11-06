package com.example.travelassistant.features.luggage.domain.repository

import com.example.travelassistant.core.domain.entity.PersonalItem

/**
 * Репозиторий для списка личных вещей
 *
 * @author Marianne Sabanchieva
 */

interface LuggageRepository {

    suspend fun getAllItems(): List<PersonalItem>
    suspend fun addItem(item: PersonalItem)
    suspend fun deleteItem(id: Int)

}