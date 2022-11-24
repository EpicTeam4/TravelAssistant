package com.example.travelassistant.features.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.R
import com.example.travelassistant.features.cities.domain.CitiesUseCase
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val citiesUseCase: CitiesUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<PlacesContract.State> =
        MutableStateFlow(PlacesContract.State.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<PlacesContract.Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _navigationEvent: Channel<PlacesContract.NavigationEvent> = Channel()
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

    private fun handleEvent(event: PlacesContract.Event) {
        when (event) {
            is PlacesContract.Event.OnViewReady -> loadAndShowPlaces(event.cityId)
            is PlacesContract.Event.OnPlaceClick -> showPlace(event.placeId)
            is PlacesContract.Event.AddPlaceToFavoritesClick -> addPlaceToFavorites(event.place)
        }
    }

    private fun loadAndShowPlaces(cityId: String) {
        sendState(PlacesContract.State.Loading)
        viewModelScope.launch {
            val result = citiesUseCase.getPlaces(cityId)
            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    sendState(PlacesContract.State.Content(places = result.getOrDefault(emptyList())))
                } else {
                    sendState(
                        PlacesContract.State.Error(
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

    private fun showPlace(placeId: String) {
        sendNavigationEvent(PlacesContract.NavigationEvent.NavigateToPlace(placeId))
    }

    private fun addPlaceToFavorites(place: PlaceDomain) {
        viewModelScope.launch {
            if (place.isUserFavorite) {
                citiesUseCase.deleteSightFromFavourite(place)
            } else {
                citiesUseCase.addSightToFavourite(place)
            }
        }
    }

    private fun sendState(state: PlacesContract.State) {
        viewModelScope.launch { _uiState.emit(state) }
    }

    fun sendEvent(event: PlacesContract.Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    fun sendNavigationEvent(event: PlacesContract.NavigationEvent) {
        viewModelScope.launch { _navigationEvent.send(event) }
    }

}