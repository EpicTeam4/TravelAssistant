package com.example.travelassistant.features.travelinfo.presentation.model

/**
 * Date time - модель для описания даты и времени
 *
 * @property year - год
 * @property month - месяц
 * @property day - день
 * @property hours - часы
 * @property minutes - минуты
 * @property selectedYear - год выбранной даты
 * @property selectedMonth - месяц выбранной даты
 * @property selectedDay - день выбранной даты
 * @property selectedHour - часы выбранной даты
 * @property selectedMinute - минуты выбранной даты
 * @property timeInMillis - дата в миллисекундах
 *
 * @author Marianne Sabanchieva
 */

data class DateTime(
    val year: Int = 0,
    val month: Int = 0,
    val day: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0,
    val selectedYear: Int = 0,
    val selectedMonth: Int = 0,
    val selectedDay: Int = 0,
    val selectedHour: Int = 0,
    val selectedMinute: Int = 0,
    val timeInMillis: Long = 0
) {
    fun copyDateTime(
        year: Int = this.year,
        month: Int = this.month,
        day: Int = this.day,
        hours: Int = this.hours,
        minutes: Int = this.minutes,
        selectedYear: Int = this.selectedYear,
        selectedMonth: Int = this.selectedMonth,
        selectedDay: Int = this.selectedDay,
        selectedHour: Int = this.selectedHour,
        selectedMinute: Int = this.selectedMinute,
        timeInMillis: Long = this.timeInMillis
    ): DateTime = DateTime(year, month, day, hours, minutes, selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute, timeInMillis)
}