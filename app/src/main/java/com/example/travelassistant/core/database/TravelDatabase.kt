package com.example.travelassistant.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.travelassistant.core.database.DatabaseConst.DATABASE_VERSION
import com.example.travelassistant.core.database.dao.PortDAO
import com.example.travelassistant.core.database.dao.CityDAO
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.City

/**
 * Travel database - объявление базы данных
 *
 * @author Marianne Sabanchieva
 */

@Database(entities = [City::class, Port::class], version = DATABASE_VERSION, exportSchema = false)
abstract class TravelDatabase : RoomDatabase() {
    abstract fun city(): CityDAO
    abstract fun port(): PortDAO
}