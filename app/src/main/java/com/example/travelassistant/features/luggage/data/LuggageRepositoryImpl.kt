package com.example.travelassistant.features.luggage.data

import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.PersonalItem
import com.example.travelassistant.features.luggage.domain.repository.LuggageRepository

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class LuggageRepositoryImpl(private val dataSource: LocalDataSource) : LuggageRepository {

    override suspend fun getAllItems(): List<PersonalItem> = dataSource.getAllItems()
    override suspend fun addItem(item: PersonalItem) = dataSource.insertItem(item)

    override suspend fun deleteItem(id: Int) = dataSource.deleteItem(id)

}