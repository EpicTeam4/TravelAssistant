package com.example.travelassistant.features.cities.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.features.cities.domain.CitiesUseCase
import com.example.travelassistant.features.cities.presentation.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitiesViewModel(private val citiesUseCase: CitiesUseCase) : ViewModel() {

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> get() = _cities

    fun init() { // todo вроде как нужна. но посмотреть что будет на соотв уроке
        viewModelScope.launch {
            val result = citiesUseCase.getCities()
            withContext(Dispatchers.Main) {
                _cities.postValue(result.map { City(it.id, it.name, it.imageUrl!!) })
            }
        }
    }

}