package com.example.travelassistant.features.editinfo.presentation.model

/**
 * Date time - модель для описания даты и времени
 *
 * @property year - год
 * @property month - месяц
 * @property day - день
 * @property hours - часы
 * @property minutes - минуты
 *
 * @author Marianne Sabanchieva
 */

data class InfoEditingDateTime(
    val year: Int = 0,
    val month: Int = 0,
    val day: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0
) {
    fun copyEditDateTime(
        year: Int = this.year,
        month: Int = this.month,
        day: Int = this.day,
        hours: Int = this.hours,
        minutes: Int = this.minutes
    ) = InfoEditingDateTime(year, month, day, hours, minutes)
}