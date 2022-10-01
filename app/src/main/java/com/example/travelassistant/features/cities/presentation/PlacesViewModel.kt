package com.example.travelassistant.features.cities.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.features.cities.domain.CitiesUseCase
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlacesViewModel(private val citiesUseCase: CitiesUseCase) : ViewModel() {

    private val _places = MutableLiveData<List<PlaceDomain>>()
    val places: LiveData<List<PlaceDomain>> get() = _places

    fun selectCity(cityId: String) {
        viewModelScope.launch {
            Log.d("cityId: ", cityId)
            val result = citiesUseCase.getPlaces(cityId)
            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    _places.setValue(result.getOrNull())
                } else {
                    // todo show error
                }
            }
        }
    }

}