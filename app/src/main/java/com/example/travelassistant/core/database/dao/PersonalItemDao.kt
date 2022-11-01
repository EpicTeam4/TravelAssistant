package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travelassistant.core.domain.entity.PersonalItem

/**
* Personal item dao - операции, производимые с данными из таблицы
*
* @author Marianne Sabanchieva
*/

@Dao
interface PersonalItemDao {
    @Query("Select * From ${PersonalItem.Schema.TABLE_NAME}")
    suspend fun getAllItems(): List<PersonalItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PersonalItem)
}