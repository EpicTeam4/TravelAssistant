package com.example.travelassistant.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.travelassistant.core.database.dao.SightsDao
import com.example.travelassistant.core.database.dao.TravelInfoDao
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.domain.entity.Sights

@Database(
    entities = [InfoAboutTravel::class, Sights::class],
    version = DatabaseConst.DATABASE_VERSION,
    exportSchema = false
)
abstract class TravelInfoDatabase : RoomDatabase() {
    abstract fun details(): TravelInfoDao
    abstract fun sights(): SightsDao
}