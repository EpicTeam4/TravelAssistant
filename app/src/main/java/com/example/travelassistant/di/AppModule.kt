package com.example.travelassistant.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.travelassistant.core.database.DatabaseConst.DATABASE_NAME
import com.example.travelassistant.core.database.TravelDatabase
import com.example.travelassistant.core.database.dao.PortDao
import com.example.travelassistant.core.database.dao.CityDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * App module - модуль для объявления контекста и создания базы данных из файла
 *
 * @author Marianne Sabanchieva
 */

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    fun provideCityDao(appDatabase: TravelDatabase): CityDao = appDatabase.city()

    @Provides
    fun providePortDao(appDatabase: TravelDatabase): PortDao = appDatabase.port()

    @Provides
    @Singleton
    fun provideDatabase(context: Context): TravelDatabase =
        Room.databaseBuilder(context, TravelDatabase::class.java, DATABASE_NAME)
            .createFromAsset("$DATABASE_NAME.db")
            .build()
}