package com.example.travelassistant.features.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.R
import com.example.travelassistant.features.cities.domain.CitiesUseCase
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaceDetailViewModel(private val citiesUseCase: CitiesUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<PlaceContract.State> =
        MutableStateFlow(PlaceContract.State.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<PlaceContract.Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

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

    private fun sendState(state: PlaceContract.State) {
        viewModelScope.launch { _uiState.emit(state) }
    }

    fun sendEvent(event: PlaceContract.Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    private fun handleEvent(event: PlaceContract.Event) {
        when (event) {
            is PlaceContract.Event.OnViewReady -> loadAndShowPlace(event.placeId)
            is PlaceContract.Event.AddPlaceToFavoritesClick -> addPlaceToFavorites()
        }
    }

    fun loadAndShowPlace(placeId: String) {
        sendState(PlaceContract.State.Loading)
        viewModelScope.launch {
            val result = citiesUseCase.getPlace(placeId)
            withContext(Dispatchers.Main) {

                if (result.isSuccess) {
                    sendState(PlaceContract.State.Content(place = result.getOrDefault(PlaceDomain())))
                } else {
                    sendState(
                        PlaceContract.State.Error(
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

    private fun addPlaceToFavorites() {
        _uiState.value.let { state ->
            if (state is PlaceContract.State.Content) {
                viewModelScope.launch {
                    if (state.place.isUserFavorite) {
                        citiesUseCase.deleteSightFromFavourite(state.place)
                    } else {
                        citiesUseCase.addSightToFavourite(state.place)
                    }
                }
            }

        }
    }

}