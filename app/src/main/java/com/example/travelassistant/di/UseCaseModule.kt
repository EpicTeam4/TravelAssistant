package com.example.travelassistant.di

import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCase
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {
    @Binds
    fun bindsInfoUseCase(useCase: GetInfoUseCaseImpl): GetInfoUseCase
}