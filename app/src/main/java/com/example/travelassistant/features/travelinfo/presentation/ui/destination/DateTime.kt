package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import java.util.Calendar
import java.util.GregorianCalendar

/**
 * Date time - выбор даты и времени
 *
 * @author Marianne Sabanchieva
 */

open class DateTime : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private val cal = Calendar.getInstance()
    private var year = 0
    private var month = 0
    private var day = 0
    private var hours = 0
    private var minutes = 0

    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0
    private var selectedHour = 0
    private var selectedMinute = 0

    var timeInMillis: Long = 0

    private fun setDateTime() {
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)
        hours = cal.get(Calendar.HOUR_OF_DAY)
        minutes = cal.get(Calendar.MINUTE)
    }

    protected fun pickDate() {
        setDateTime()
        DatePickerDialog(requireContext(), this, year, month, day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        selectedYear = year
        selectedMonth = month
        selectedDay = day

        setDateTime()
        TimePickerDialog(requireContext(), this, hours, minutes, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute

        timeInMillis = GregorianCalendar(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute).timeInMillis
    }
}