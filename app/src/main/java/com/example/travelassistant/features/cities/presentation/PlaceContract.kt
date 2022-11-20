package com.example.travelassistant.features.cities.presentation

import com.example.travelassistant.features.cities.domain.model.PlaceDomain

class PlaceContract {

    sealed class State {
        object Loading : State()
        data class Error(val errorModel: ErrorModel) : State()
        data class Content(val place: PlaceDomain) : State()
    }

    sealed class Event {
        data class OnViewReady(val placeId: String) : Event()
        object AddPlaceToFavoritesClick : Event()
    }

}