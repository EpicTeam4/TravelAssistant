package com.example.travelassistant.features.cities.presentation

import com.example.travelassistant.features.cities.domain.model.CityDomain

class CitiesContract {

    sealed class State {
        object Loading : State()
        data class Error(val errorModel: ErrorModel) : State()
        data class Content(val cities: List<CityDomain> = emptyList()) : State()
    }

    sealed class NavigationEvent {
        data class NavigateToPlaces(val cityId: String) : NavigationEvent()
    }

    sealed class Event {
        object OnViewReady: Event()
        data class OnCityClick(val cityId: String) : Event()
    }

}