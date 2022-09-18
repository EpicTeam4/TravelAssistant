package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.travelassistant.core.domain.entity.City

@Dao
interface CityDAO {
    @Query("Select * From ${City.Schema.TABLE_NAME}")
    suspend fun getAllCities(): List<City>

    @Query("Select * From ${City.Schema.TABLE_NAME} WHERE ${City.Schema.ID} = :id")
    suspend fun getCityById(id: Long): City
}