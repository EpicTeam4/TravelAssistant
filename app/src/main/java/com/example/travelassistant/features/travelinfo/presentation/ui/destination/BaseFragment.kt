package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.travelassistant.core.toNavigate
import com.example.travelassistant.features.travelinfo.presentation.ui.TravelInfoViewModel
import com.example.travelassistant.core.commands.GoToFragment
import com.example.travelassistant.core.commands.ViewCommand
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

    protected open fun handleCommand(viewCommand: ViewCommand) {
        if (viewCommand is GoToFragment) goToFragment(viewCommand)
    }

    private fun goToFragment(viewCommand: GoToFragment) {
        requireActivity().toNavigate(viewCommand.pathId)
    }

    protected fun pickDate(id: String) {
        val currentDate = infoViewModel.formatter.getCurrentDate()
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
                        if (id == TIME_ID) {
                            infoViewModel.getDateInMillis()
                        } else {
                            infoViewModel.getDateDestInMillis()
                        }
                    },
                    currentDate[Calendar.HOUR_OF_DAY],
                    currentDate[Calendar.MINUTE], false
                ).show()
            }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH],
            currentDate[Calendar.DATE]
        ).show()
    }

    companion object {
        const val TIME_ID = "time"
        const val TIME_DEST_ID = "timeDest"
    }
}