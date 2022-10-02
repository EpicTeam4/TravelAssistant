package com.example.travelassistant.features.cities.di

import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.features.cities.data.CitiesRepository
import com.example.travelassistant.features.cities.domain.CitiesUseCase
import com.example.travelassistant.features.cities.presentation.CitiesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val citiesModule = module {

    single { KudagoClient() }
    single { CitiesRepository(get()) }
    single { CitiesUseCase(get()) }

    viewModel { CitiesViewModel(get()) }

//    fragment { CitiesFragment(get()) }

}