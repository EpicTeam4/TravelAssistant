package com.example.travelassistant.di

import android.app.Application
import android.content.Context
import com.example.travelassistant.BaseFragment
import com.example.travelassistant.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * App component - компонент даггера
 *
 * @author Marianne Sabanchieva
 */

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, UseCaseModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(frag: BaseFragment)
    fun inject(activity: MainActivity)
    fun context() : Context

    @Component.Builder
    interface BuilderComponent {
        @BindsInstance
        fun appModule(application: Application): BuilderComponent
        fun buildAppComp(): AppComponent
    }
}