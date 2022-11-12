package com.example.travelassistant.features.cities.presentation

import com.example.travelassistant.features.cities.domain.model.PlaceDomain

class PlacesContract {

    sealed class State {
        object Loading : State()
        data class Error(val errorModel: ErrorModel) : State()
        data class Content(val places: List<PlaceDomain> = emptyList()) : State()
    }

    sealed class NavigationEvent {
        data class NavigateToPlace(val placeId: String) : NavigationEvent()
    }

    sealed class Event {
        data class OnViewReady(val cityId: String): Event()
        data class OnPlaceClick(val placeId: String) : Event()
    }

}