package com.example.travelassistant.features.travelinfo.presentation.model

/**
 * Selected hour - модель, содержащая выбранное время
 *
 * @author Marianne Sabanchieva
 */

data class SelectedHour(
    val hours: Long = 0
) {
    fun copySelectedHour(hours: Long = this.hours): SelectedHour = SelectedHour(hours = hours)
}