package com.example.travelassistant.features.travelinfo.presentation.ui

import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.features.travelinfo.data.model.ErrorModel

/**
 * View states - описывают состояние экрана в момент времени
 *
 * @author Marianne Sabanchieva
 */

sealed class TravelInfoViewState {
    data class Error(val errorModel: ErrorModel) : TravelInfoViewState()
    data class Content(
        val cities: List<City> = listOf(),
        val ports: List<Port> = listOf(),
        val hotels: List<Hotel> = listOf()
    ) : TravelInfoViewState()
}