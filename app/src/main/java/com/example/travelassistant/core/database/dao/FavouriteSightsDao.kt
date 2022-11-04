package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.travelassistant.core.domain.entity.FavouriteSights

/**
 * Favourite sights dao - операции, производимые с данными из таблицы
 *
 * @author Marianne Sabanchieva
 */

@Dao
interface FavouriteSightsDao {
    @Transaction
    @Query("Select * From ${FavouriteSights.Schema.TABLE_NAME}")
    suspend fun getFavouriteSights(): List<FavouriteSights>

    @Query("Select * From ${FavouriteSights.Schema.TABLE_NAME} Where ${FavouriteSights.Schema.ID} = :id")
    suspend fun getSightsById(id: Int): FavouriteSights?
}