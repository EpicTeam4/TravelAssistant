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
import com.example.travelassistant.core.database.dao.PersonalItemDao
import com.example.travelassistant.core.database.dao.TravelInfoDao
import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.PersonalItem
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.network.ApiMapperHotel
import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.core.network.KudagoClientApi
import com.example.travelassistant.features.travelinfo.data.InfoRepositoryImpl
import com.example.travelassistant.features.travelinfo.domain.State
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCase
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
    suspend fun providesInfoUseCase(useCase: GetInfoUseCase): State<List<City>> = useCase.getCities()

    @Provides
    suspend fun providesPortUseCase(useCase: GetInfoUseCase): State<List<Port>> = useCase.getPorts()

    @Provides
    suspend fun providesHotelUseCase(useCase: GetInfoUseCase, location: String): State<List<Hotel>> =
        useCase.getHotels(location)

    @Provides
    suspend fun providesItemUseCase(useCase: GetInfoUseCase): State<List<PersonalItem>> =
        useCase.getAllItems()

    @Provides
    suspend fun providesDetailsUseCase(useCase: GetInfoUseCase, info: InfoAboutTravel) =
        useCase.addDetails(info)

    @Provides
    fun bindsInfoRepository(dataSource: LocalDataSource, hotelMapper: ApiMapperHotel): InfoRepository =
        InfoRepositoryImpl(dataSource, hotelMapper)

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