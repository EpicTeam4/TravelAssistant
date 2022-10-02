package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.travelassistant.features.travelinfo.presentation.model.DateTime
import com.example.travelassistant.features.travelinfo.presentation.utils.DateTimeFormatter
import java.util.Calendar

/**
 * Date time - выбор даты и времени
 *
 * @author Marianne Sabanchieva
 */

open class DateTimePickerFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    var selectedDateTime = DateTime()
    private val formatter = DateTimeFormatter()

    private fun setDateTime() {
        with(formatter.getCurrentDate()) {
            selectedDateTime = selectedDateTime.copyDateTime(
                year = get(Calendar.YEAR),
                month = get(Calendar.MONTH),
                day = get(Calendar.DAY_OF_MONTH),
                hours = get(Calendar.HOUR_OF_DAY),
                minutes = get(Calendar.MINUTE)
            )
        }
    }

    protected fun pickDate() {
        setDateTime()
        DatePickerDialog(requireContext(), this, selectedDateTime.year, selectedDateTime.month, selectedDateTime.day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        selectedDateTime = selectedDateTime.copyDateTime(selectedYear = year, selectedMonth = month, selectedDay = day)

        setDateTime()
        TimePickerDialog(requireContext(), this, selectedDateTime.hours, selectedDateTime.minutes, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        selectedDateTime = selectedDateTime.copyDateTime(selectedHour = hour, selectedMinute = minute)

        with(selectedDateTime) {
            selectedDateTime = copyDateTime(timeInMillis = formatter.getSelectedDateTimeInMillis(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute))
        }
    }
}