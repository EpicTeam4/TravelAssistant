package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.travelassistant.core.domain.entity.Port

/**
 * Port dao - операции, производимые с данными из таблицы
 *
 * @author Marianne Sabanchieva
 */

@Dao
interface PortDao {
    @Query("Select * From ${Port.Schema.TABLE_NAME}")
    suspend fun getAllPorts(): List<Port>
}