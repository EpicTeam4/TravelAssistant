package com.example.travelassistant.features.travelinfo.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.features.travelinfo.domain.entity.City
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model для хранения списка городов
 *
 * @author Marianne Sabanchieva
 */

class InfoViewModel @Inject constructor(
    private val useCase: GetInfoUseCaseImpl
) : ViewModel() {
    private val _cityLoaded = MutableLiveData<List<City>>()
    val allCities: LiveData<List<City>> get() = _cityLoaded

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            _cityLoaded.postValue(useCase.getCities())
        }
    }
}