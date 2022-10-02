package com.example.travelassistant.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.travelassistant.core.database.DatabaseConst.DATABASE_NAME
import com.example.travelassistant.core.database.TravelDatabase
import com.example.travelassistant.core.database.dao.PortDao
import com.example.travelassistant.core.database.dao.CityDao
import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.features.travelinfo.data.InfoRepositoryImpl
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * App module - модуль для объявления контекста и создания базы данных из файла
 *
 * @author Marianne Sabanchieva
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    fun provideCityDao(appDatabase: TravelDatabase): CityDao = appDatabase.city()

    @Provides
    fun providePortDao(appDatabase: TravelDatabase): PortDao = appDatabase.port()

    @Provides
    suspend fun providesInfoUseCase(useCase: GetInfoUseCase): List<City> = useCase.getCities()

    @Provides
    suspend fun providesPortUseCase(useCase: GetInfoUseCase): List<Port> = useCase.getPorts()

    @Provides
    fun bindsInfoRepository(dataSource: LocalDataSource): InfoRepository =
        InfoRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): TravelDatabase =
        Room.databaseBuilder(context, TravelDatabase::class.java, DATABASE_NAME)
            .createFromAsset("$DATABASE_NAME.db")
            .build()
}