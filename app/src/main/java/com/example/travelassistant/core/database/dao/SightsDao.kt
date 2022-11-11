package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.travelassistant.core.domain.entity.Sights

/**
 * Sights dao - операции, производимые с данными из таблицы
 *
 * @author Marianne Sabanchieva
 */

@Dao
interface SightsDao {
    @Transaction
    @Query("Select * From ${Sights.Schema.TABLE_NAME}")
    suspend fun getFavouriteSights(): List<Sights>

    @Query("Select * From ${Sights.Schema.TABLE_NAME} Where ${Sights.Schema.ID} = :id")
    suspend fun getSightsById(id: Int): Sights?

    @Query("Delete From ${Sights.Schema.TABLE_NAME} Where ${Sights.Schema.ID} = :id")
    suspend fun deleteSightsFromFavourite(id: Int)
}