package com.example.travelassistant.features.travelinfo.presentation.utils

/**
 * Adapter position to hours - переводит выбранную позицию в число
 *
 * @property pos - позиция в списке
 * @property hours - часы
 *
 * @author Marianne Sabanchieva
 */

sealed class AdapterPositionToHours(val pos: Int, val hours: Long) {
    object Three : AdapterPositionToHours(pos = 1, hours = DateTimeFormatter().convertHoursToMilliseconds(3))
    object Five : AdapterPositionToHours(pos = 2, hours = DateTimeFormatter().convertHoursToMilliseconds(5))
    object Twelve : AdapterPositionToHours(pos = 3, hours = DateTimeFormatter().convertHoursToMilliseconds(12))
    object Day : AdapterPositionToHours(pos = 4, hours = DateTimeFormatter().convertHoursToMilliseconds(24))
    object ThreeDays : AdapterPositionToHours(pos = 5, hours = DateTimeFormatter().convertHoursToMilliseconds(72))
    object Unknown : AdapterPositionToHours(pos = 0, 0)
}

fun Int.toHours(): Long =
    when (this) {
        AdapterPositionToHours.Three.pos -> AdapterPositionToHours.Three.hours
        AdapterPositionToHours.Five.pos -> AdapterPositionToHours.Five.hours
        AdapterPositionToHours.Twelve.pos -> AdapterPositionToHours.Twelve.hours
        AdapterPositionToHours.Day.pos -> AdapterPositionToHours.Day.hours
        AdapterPositionToHours.ThreeDays.pos -> AdapterPositionToHours.ThreeDays.hours
        else -> AdapterPositionToHours.Unknown.hours
    }