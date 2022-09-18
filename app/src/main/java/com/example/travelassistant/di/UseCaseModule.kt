package com.example.travelassistant.di

import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    suspend fun providesInfoUseCase(useCase: GetInfoUseCaseImpl): List<City> = useCase.getCities()
}