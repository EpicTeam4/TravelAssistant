package com.example.travelassistant.features.travelinfo.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCase
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * View model для хранения списка городов
 *
 * @author Marianne Sabanchieva
 */

class InfoViewModel @Inject constructor(
    private val useCase: GetInfoUseCase
) : ViewModel() {
    private val _cityLoaded = MutableLiveData<List<City>>()
    val allCities: LiveData<List<City>> get() = _cityLoaded

    fun init() {
        viewModelScope.launch {
            val result = useCase.getCities()
            withContext(Main) {
                _cityLoaded.postValue(result)
            }
        }
    }
}