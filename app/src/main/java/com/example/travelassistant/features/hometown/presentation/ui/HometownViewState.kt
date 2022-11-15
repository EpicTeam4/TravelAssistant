package com.example.travelassistant.features.hometown.presentation.ui

import com.example.travelassistant.core.data.model.ErrorModel
import com.example.travelassistant.core.domain.entity.City

/**
 * View states - описывают состояние экрана в момент времени
 *
 * @author Marianne Sabanchieva
 */

sealed class HometownViewState {
    object Loading : HometownViewState()
    data class Error(val errorModel: ErrorModel) : HometownViewState()
    data class Content(
        val cities: List<City> = listOf(),
        val cityId: Int = 0
    ) : HometownViewState()
}