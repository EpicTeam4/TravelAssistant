package com.example.travelassistant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Place application - инициализирует Hilt
 *
 * @author Marianne Sabanchieva
 */

@HiltAndroidApp
class TravelAssistantApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}