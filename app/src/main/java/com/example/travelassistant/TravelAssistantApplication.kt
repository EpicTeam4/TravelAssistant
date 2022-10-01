package com.example.travelassistant

import android.app.Application
import com.example.travelassistant.features.cities.di.citiesModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Place application - инициализирует Hilt
 *
 * @author Marianne Sabanchieva
 */

@HiltAndroidApp
class TravelAssistantApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TravelAssistantApplication)
            modules(citiesModule)
        }
    }

}