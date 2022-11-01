package com.example.travelassistant.core.utils

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

/**
 * Hours to adapter position - переводит выбранное время в позицию spinner
 *
 * @property pos - позиция в списке
 * @property hours - часы
 *
 * @author Marianne Sabanchieva
 */

sealed class HoursToAdapterPosition(val hours: Int, val pos: Int) {
    object Three : HoursToAdapterPosition(hours = 3, pos = 1)
    object Five : HoursToAdapterPosition(hours = 5, pos = 2)
    object Twelve : HoursToAdapterPosition(hours = 12, pos = 3)
    object Day : HoursToAdapterPosition(hours = 24, pos = 4)
    object ThreeDays : HoursToAdapterPosition(hours = 72, pos = 5)
    object Unknown : HoursToAdapterPosition(hours = 0, pos = 0)
}

fun Int.toPosition(): Int =
    when (this) {
        HoursToAdapterPosition.Three.hours -> HoursToAdapterPosition.Three.pos
        HoursToAdapterPosition.Five.hours -> HoursToAdapterPosition.Five.pos
        HoursToAdapterPosition.Twelve.hours -> HoursToAdapterPosition.Twelve.pos
        HoursToAdapterPosition.Day.hours -> HoursToAdapterPosition.Day.pos
        HoursToAdapterPosition.ThreeDays.hours -> HoursToAdapterPosition.ThreeDays.pos
        else -> HoursToAdapterPosition.Unknown.pos
    }