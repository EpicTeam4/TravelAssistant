package com.example.travelassistant.di

import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    suspend fun providesInfoUseCase(useCase: GetInfoUseCase): List<City> = useCase.getCities()
}