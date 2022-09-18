package com.example.travelassistant.di

import com.example.travelassistant.features.travelinfo.data.InfoRepositoryImpl
import com.example.travelassistant.features.travelinfo.domain.repository.InfoRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindsInfoRepository(placeRepository: InfoRepositoryImpl): InfoRepository
}