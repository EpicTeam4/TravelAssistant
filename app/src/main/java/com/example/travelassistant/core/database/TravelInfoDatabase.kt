package com.example.travelassistant.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.travelassistant.core.database.dao.FavouriteSightsDao
import com.example.travelassistant.core.database.dao.TravelInfoDao
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.domain.entity.FavouriteSights

@Database(
    entities = [InfoAboutTravel::class, FavouriteSights::class],
    version = DatabaseConst.DATABASE_VERSION,
    exportSchema = false
)
abstract class TravelInfoDatabase : RoomDatabase() {
    abstract fun details(): TravelInfoDao
    abstract fun sights(): FavouriteSightsDao
}