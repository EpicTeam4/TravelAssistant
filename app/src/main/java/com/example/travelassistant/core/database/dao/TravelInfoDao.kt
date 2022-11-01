package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.travelassistant.core.domain.entity.InfoAboutTravel

/**
 * Travel info dao - операции, производимые с данными из таблицы
 *
 * @author Marianne Sabanchieva
 */

@Dao
interface TravelInfoDao {
    @Query(
        "Select * From ${InfoAboutTravel.Schema.TABLE_NAME} " +
                "WHERE ${InfoAboutTravel.Schema.TIME_DEST} > :date " +
                "ORDER BY ${InfoAboutTravel.Schema.TIME_DEST} DESC LIMIT 1"
    )
    suspend fun getInfo(date: Long): InfoAboutTravel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(info: InfoAboutTravel)

    @Update
    suspend fun updateDetails(info: InfoAboutTravel)
}