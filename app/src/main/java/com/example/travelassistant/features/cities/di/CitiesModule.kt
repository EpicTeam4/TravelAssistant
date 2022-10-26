package com.example.travelassistant.features.cities.di

import androidx.room.Room
import com.example.travelassistant.core.database.DatabaseConst
import com.example.travelassistant.core.database.TravelDatabase
import com.example.travelassistant.core.database.TravelInfoDatabase
import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.features.cities.data.CitiesRepositoryLocalDbImpl
import com.example.travelassistant.features.cities.data.PlacesRepositoryImpl
import com.example.travelassistant.features.cities.domain.CitiesUseCase
import com.example.travelassistant.features.cities.presentation.CitiesViewModel
import com.example.travelassistant.features.cities.presentation.PlaceDetailViewModel
import com.example.travelassistant.features.cities.presentation.PlacesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val citiesModule = module {

    single {
        Room.databaseBuilder(
            get(),
            TravelDatabase::class.java,
            DatabaseConst.DATABASE_NAME
        )
            .createFromAsset("${DatabaseConst.DATABASE_NAME}.db")
            .build()
    }
    single {
        Room.databaseBuilder(
            get(),
            TravelInfoDatabase::class.java,
            DatabaseConst.DATABASE_INFO_NAME
        )
            .createFromAsset("${DatabaseConst.DATABASE_INFO_NAME}.db")
            .build()
    }
    single {
        val database = get<TravelDatabase>()
        database.city()
    }
    single {
        val database = get<TravelDatabase>()
        database.port()
    }
    single {
        val database = get<TravelDatabase>()
        database.item()
    }
    single {
        val database = get<TravelInfoDatabase>()
        database.details()
    }
    single {
        val database = get<TravelInfoDatabase>()
        database.sights()
    }
    single { LocalDataSource(city = get(), airport = get(), item = get(), details = get(), sights = get()) }
    single { KudagoClient() }
    single { CitiesRepositoryLocalDbImpl(get()) }
    single { PlacesRepositoryImpl(get()) }
    single {
        CitiesUseCase(
            citiesRepository = CitiesRepositoryLocalDbImpl(get()),
            placesRepository = PlacesRepositoryImpl(get())
        )
    }

    viewModel { CitiesViewModel(get()) }
    viewModel { PlacesViewModel(get()) }
    viewModel { PlaceDetailViewModel (get()) }

}