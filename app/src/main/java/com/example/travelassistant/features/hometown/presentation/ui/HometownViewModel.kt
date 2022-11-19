package com.example.travelassistant.features.hometown.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.parseError
import com.example.travelassistant.features.hometown.domain.usecase.GetHometownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * View model для хранения списка городов
 *
 * @author Marianne Sabanchieva
 */

@HiltViewModel
class HometownViewModel @Inject constructor(
    private val useCase: GetHometownUseCase
) : ViewModel() {

    var content = HometownViewState.Content()

    private val dataContent = MutableLiveData<HometownViewState>()
    val dataState: LiveData<HometownViewState> get() = dataContent

    fun loadData() {
        viewModelScope.launch {
            useCase.getHometown().collect() {
                loadCities(it)
            }
        }
    }

    private fun loadCities(cityId: Int) {
        viewModelScope.launch {
            dataContent.value = HometownViewState.Loading
            when (val cities = useCase.getCities()) {
                is State.Success -> handleData(
                    cities = cities.data, cityId = if (cityId != DEFAULT_VALUE) {
                        cityId - 1
                    } else {
                        DEFAULT_VALUE
                    }
                )
                is State.Error -> handleError(false)
            }
        }
    }

    private suspend fun handleData(cities: List<City>, cityId: Int) {
        withContext(Dispatchers.Main) {
            dataContent.value = content.copy(cities = cities, cityId = cityId)
        }
    }

    private suspend fun handleError(isNetworkError: Boolean) {
        withContext(Dispatchers.Main) {
            dataContent.value = HometownViewState.Error(isNetworkError.parseError())
        }
    }

    fun savePreferences(cityId: Int) {
        viewModelScope.launch {
            useCase.rewriteHometown(cityId)
        }
    }

    companion object {
        const val DEFAULT_VALUE = 0
    }
}