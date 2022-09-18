package com.example.travelassistant

import android.app.Application
import com.example.travelassistant.di.AppComponent
import com.example.travelassistant.di.DaggerAppComponent

/**
 * Place application - инициализирует компонент даггера
 *
 * @author Marianne Sabanchieva
 */

open class TravelAssistantApplication : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()

        initComponent()
    }

    protected open fun initComponent() {
        dagger = DaggerAppComponent
            .builder()
            .appModule(this)
            .buildAppComp()
    }
}