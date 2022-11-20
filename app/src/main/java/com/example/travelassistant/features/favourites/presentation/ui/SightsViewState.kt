package com.example.travelassistant.features.favourites.presentation.ui

import com.example.travelassistant.core.data.model.ErrorModel
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.cities.domain.model.PlaceDomain

/**
 * View states - описывают состояние экрана в момент времени
 *
 * @author Marianne Sabanchieva
 */

sealed class SightsViewState {
    object Loading : SightsViewState()
    data class Error(val errorModel: ErrorModel) : SightsViewState()
    data class Content(
        val cities: List<City> = listOf(),
        val sights: List<PlaceDomain> = listOf()
    ) : SightsViewState()
}