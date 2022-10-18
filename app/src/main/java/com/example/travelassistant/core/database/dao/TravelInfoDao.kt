package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travelassistant.core.domain.entity.InfoAboutTravel

@Dao
interface TravelInfoDao {
    @Query("Select * From ${InfoAboutTravel.Schema.TABLE_NAME}")
    suspend fun getDetails(): List<InfoAboutTravel>

    @Query("Select * From ${InfoAboutTravel.Schema.TABLE_NAME} WHERE ${InfoAboutTravel.Schema.TIME_DEST} > :date")
    suspend fun getInfo(date: Long): InfoAboutTravel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(info: InfoAboutTravel)
}