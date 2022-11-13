package com.example.travelassistant.features.luggage.presentation.ui

import com.example.travelassistant.core.data.model.ErrorModel
import com.example.travelassistant.core.domain.entity.PersonalItem

/**
 * View states - описывают состояние экрана в момент времени
 *
 * @author Marianne Sabanchieva
 */

sealed class LuggageViewState {
    object Loading : LuggageViewState()
    data class Error(val errorModel: ErrorModel) : LuggageViewState()
    data class Content(val items: List<PersonalItem> = listOf()) : LuggageViewState()
}