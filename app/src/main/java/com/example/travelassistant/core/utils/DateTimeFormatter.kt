package com.example.travelassistant.core.utils

import com.example.travelassistant.core.Constants.EMPTY_STRING
import com.example.travelassistant.core.Constants.MILLISECONDS_IN_SECONDS
import com.example.travelassistant.core.Constants.RESULT_DATE_FORMAT
import com.example.travelassistant.core.Constants.SECONDS_IN_MINUTE
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import javax.inject.Inject

class DateTimeFormatter @Inject constructor() {

    /**
     * Convert long date to string - конвертирует дату из Long в строку
     *
     * @author Marianne Sabanchieva
     */

    fun convertLongDateToString(date: Long): String {
        return if (date > 0) SimpleDateFormat(RESULT_DATE_FORMAT).format(date) else EMPTY_STRING
    }

    /**
     * Convert hours to milliseconds - конвертирует часы в миллисекунды
     *
     * @author Marianne Sabanchieva
     */

    fun convertHoursToMilliseconds(hour: Int): Long =
        (hour * SECONDS_IN_MINUTE * SECONDS_IN_MINUTE * MILLISECONDS_IN_SECONDS).toLong()

    /**
     * Convert milliseconds to hours - конвертирует миллисекунды в часы
     *
     * @author Marianne Sabanchieva
     */

    fun convertMillisecondsToHours(time: Long): Int =
        (time / SECONDS_IN_MINUTE / SECONDS_IN_MINUTE / MILLISECONDS_IN_SECONDS).toInt()

    /**
     * Get current date - инициализирует текущую дату
     *
     * @author Marianne Sabanchieva
     */

    fun getCurrentDate(): Calendar = Calendar.getInstance()

    /**
     * Get selected date time in millis - переводит выбранные дату и время в миллисекунды
     *
     * @param year - год выбранной даты
     * @param month - месяц выбранной даты
     * @param day - день выбранной даты
     * @param hour - часы выбранной даты
     * @param minute - минуты выбранной даты
     *
     * @author Marianne Sabanchieva
     */

    fun getSelectedDateTimeInMillis(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long =
        GregorianCalendar(year, month, day, hour, minute).timeInMillis

}