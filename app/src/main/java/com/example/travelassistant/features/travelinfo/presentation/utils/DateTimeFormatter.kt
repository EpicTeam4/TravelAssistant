package com.example.travelassistant.features.travelinfo.presentation.utils

import com.example.travelassistant.core.Constants.MILLISECONDS_IN_SECONDS
import com.example.travelassistant.core.Constants.RESULT_DATE_FORMAT
import com.example.travelassistant.core.Constants.SECONDS_IN_MINUTE
import java.text.SimpleDateFormat

/**
 * Convert long date to string - конвертирует дату из Long в строку
 *
 * @author Marianne Sabanchieva
 */

fun convertLongDateToString(date: Long): String {
    val dateToString = SimpleDateFormat(RESULT_DATE_FORMAT)
    var result = ""
    if (date > 0) result = dateToString.format(date)
    return result
}

/**
 * Convert hours to milliseconds - конвертирует часы в миллисекунды
 *
 * @author Marianne Sabanchieva
 */

fun convertHoursToMilliseconds(hour: Int): Long {
    return (hour*SECONDS_IN_MINUTE*SECONDS_IN_MINUTE*MILLISECONDS_IN_SECONDS).toLong()
}