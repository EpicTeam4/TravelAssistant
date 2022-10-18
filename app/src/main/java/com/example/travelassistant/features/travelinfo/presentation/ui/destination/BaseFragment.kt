package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.travelassistant.core.toNavigate
import com.example.travelassistant.features.travelinfo.presentation.ui.TravelInfoViewModel
import com.example.travelassistant.features.travelinfo.presentation.ui.commands.GoToFragment
import com.example.travelassistant.features.travelinfo.presentation.ui.commands.ViewCommand
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

/**
 * Date time - выбор даты и времени
 *
 * @author Marianne Sabanchieva
 */

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    private val infoViewModel: TravelInfoViewModel by activityViewModels()

    protected fun handleCommand(viewCommand: ViewCommand) {
        if (viewCommand is GoToFragment) goToFragment(viewCommand)
    }

    private fun goToFragment(viewCommand: GoToFragment) {
        requireActivity().toNavigate(viewCommand.pathId)
    }


    protected fun pickDate() {
        val currentDate = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                infoViewModel.selectedDateTime =
                    infoViewModel.selectedDateTime.copyDateTime(
                        year = year, month = monthOfYear, day = dayOfMonth
                    )
                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        infoViewModel.selectedDateTime =
                            infoViewModel.selectedDateTime.copyDateTime(
                                hours = hourOfDay, minutes = minute
                            )
                        dateLong()
                    },
                    currentDate[Calendar.HOUR_OF_DAY],
                    currentDate[Calendar.MINUTE], false
                ).show()
            }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH],
            currentDate[Calendar.DATE]
        ).show()
    }

    private fun dateLong(): Long {
        infoViewModel.tempDate = with(infoViewModel.selectedDateTime) {
            infoViewModel.formatter.getSelectedDateTimeInMillis(
                year, month, day, hours, minutes)
        }
        return infoViewModel.tempDate
    }
}