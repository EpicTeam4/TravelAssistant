package com.example.travelassistant.features.editinfo.presentation.ui

import com.example.travelassistant.core.data.model.ErrorModel
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.domain.entity.Port

/**
 * View states - описывают состояние экрана в момент времени
 *
 * @author Marianne Sabanchieva
 */

sealed class InfoViewState {
    object Loading : InfoViewState()
    data class Error(val errorModel: ErrorModel) : InfoViewState()
    data class Content(
        val event: InfoAboutTravel? = null,
        val airports: List<Port> = listOf(),
        val railways: List<Port> = listOf(),
        val airportsDest: List<Port> = listOf(),
        val railwaysDest: List<Port> = listOf(),
        val hotels: List<Hotel> = listOf(),
        val cityId: Int = 0
    ) : InfoViewState()
}