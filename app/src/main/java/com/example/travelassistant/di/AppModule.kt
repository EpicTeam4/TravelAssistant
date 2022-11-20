package com.example.travelassistant.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.travelassistant.core.Constants.BASE_URL
import com.example.travelassistant.core.database.DatabaseConst.DATABASE_INFO_NAME
import com.example.travelassistant.core.database.DatabaseConst.DATABASE_NAME
import com.example.travelassistant.core.database.TravelDatabase
import com.example.travelassistant.core.database.TravelInfoDatabase
import com.example.travelassistant.core.database.dao.PortDao
import com.example.travelassistant.core.database.dao.CityDao
import com.example.travelassistant.core.database.dao.SightsDao
import com.example.travelassistant.core.database.dao.PersonalItemDao
import com.example.travelassistant.core.database.dao.TravelInfoDao
import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.network.ApiMapperHotel
import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.core.network.KudagoClientApi
import com.example.travelassistant.features.editinfo.data.InfoEditingRepositoryImpl
import com.example.travelassistant.features.editinfo.domain.repository.InfoEditingRepository
import com.example.travelassistant.features.favourites.data.SightsRepositoryImpl
import com.example.travelassistant.features.favourites.domain.repository.SightsRepository
import com.example.travelassistant.features.hometown.data.HometownRepositoryImpl
import com.example.travelassistant.features.hometown.domain.repository.HometownRepository
import com.example.travelassistant.features.luggage.data.LuggageRepositoryImpl
import com.example.travelassistant.features.luggage.domain.repository.LuggageRepository
import com.example.travelassistant.features.travelinfo.data.InfoRepositoryImpl
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideItemDao(appDatabase: TravelDatabase): PersonalItemDao = appDatabase.item()

    @Provides
    fun provideDetails(appDatabase: TravelInfoDatabase): TravelInfoDao = appDatabase.details()

    @Provides
    fun provideSights(appDatabase: TravelInfoDatabase): SightsDao = appDatabase.sights()

    /**
     * Provide kudago client api
     *
     * @author Ilya P.
     */

    @Provides
    fun provideKudagoClientApi(): KudagoClientApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(KudagoClient.RequestResponseInterceptor)
                    .build()
            )
            .baseUrl(BASE_URL)
            .build()
            .create(KudagoClientApi::class.java)

    @Provides
    fun providesInfoRepository(
        dataSource: LocalDataSource,
        hotelMapper: ApiMapperHotel
    ): InfoRepository =
        InfoRepositoryImpl(dataSource, hotelMapper)

    @Provides
    fun providesEditInfoRepository(
        dataSource: LocalDataSource,
        hotelMapper: ApiMapperHotel
    ): InfoEditingRepository =
        InfoEditingRepositoryImpl(dataSource, hotelMapper)

    @Provides
    fun providesSightsRepository(kudagoClient: KudagoClient, dataSource: LocalDataSource): SightsRepository =
        SightsRepositoryImpl(kudagoClient, dataSource)

    @Provides
    fun providesLuggageRepository(dataSource: LocalDataSource): LuggageRepository =
        LuggageRepositoryImpl(dataSource)

    @Provides
    fun providesHometownRepository(dataSource: LocalDataSource): HometownRepository =
        HometownRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): TravelDatabase =
        Room.databaseBuilder(context, TravelDatabase::class.java, DATABASE_NAME)
            .createFromAsset("$DATABASE_NAME.db")
            .build()

    @Provides
    @Singleton
    fun provideInfoDatabase(context: Context): TravelInfoDatabase =
        Room.databaseBuilder(context, TravelInfoDatabase::class.java, DATABASE_INFO_NAME).build()
}