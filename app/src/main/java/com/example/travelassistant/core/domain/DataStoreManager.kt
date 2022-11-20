package com.example.travelassistant.core.domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {

    private val settingsDataStore = context.dataStore
    private val id = intPreferencesKey(CITY_ID_KEY)

    suspend fun rewriteHometown(cityId: Int) {
        settingsDataStore.edit { settings ->
            settings[id] = cityId
        }
    }

    fun getHometown(): Flow<Int> = settingsDataStore.data
        .map { preferences ->
            preferences[id] ?: DEFAULT_ID
        }

    companion object {
        const val DEFAULT_ID = 0
        const val CITY_ID_KEY = "cityId"
    }
}