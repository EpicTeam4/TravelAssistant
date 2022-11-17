package com.example.travelassistant.features.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.R
import com.example.travelassistant.features.cities.domain.CitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitiesViewModel(private val citiesUseCase: CitiesUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<CitiesContract.State> =
        MutableStateFlow(CitiesContract.State.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<CitiesContract.Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _navigationEvent: Channel<CitiesContract.NavigationEvent> = Channel()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    private fun handleEvent(event: CitiesContract.Event) {
        when (event) {
            is CitiesContract.Event.OnViewReady -> showCities()
            is CitiesContract.Event.OnCityClick -> showPlaces(event.cityId)
        }
    }

    private fun showCities() {
        sendState(CitiesContract.State.Loading)
        viewModelScope.launch {
            val result = citiesUseCase.getCities()
            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    sendState(CitiesContract.State.Content(cities = result.getOrDefault(emptyList())))
                } else {
                    sendState(
                        CitiesContract.State.Error(
                            ErrorModel(
                                R.string.unknown_error,
                                R.drawable.unknown_error
                            )
                        )
                    )
                }
            }
        }
    }

    private fun showPlaces(cityId: String) {
        sendNavigationEvent(CitiesContract.NavigationEvent.NavigateToPlaces(cityId))
    }

    private fun sendState(state: CitiesContract.State) {
        viewModelScope.launch { _uiState.emit(state) }
    }

    fun sendEvent(event: CitiesContract.Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    fun sendNavigationEvent(event: CitiesContract.NavigationEvent) {
        viewModelScope.launch { _navigationEvent.send(event) }
    }
}