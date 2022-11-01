package com.example.travelassistant.features.editinfo.presentation.ui

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.travelassistant.R
import com.example.travelassistant.core.observe
import com.example.travelassistant.core.orDefault
import com.example.travelassistant.databinding.FragmentEditEventBinding
import com.example.travelassistant.core.commands.SetAlarm
import com.example.travelassistant.core.commands.ViewCommand
import com.example.travelassistant.core.utils.toHours
import java.util.Calendar

class InfoEditingFragment : Fragment() {

    private lateinit var portsList: ArrayAdapter<String>
    private lateinit var hotelsList: ArrayAdapter<String>
    private var _binding: FragmentEditEventBinding? = null
    private val infoViewModel: InfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_edit_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEditEventBinding.bind(view)

        initObservers()
        infoViewModel.apply {
            dataState.observe(viewLifecycleOwner, ::handleState)
            observe(infoViewModel.commands, ::handleCommand)
            loadDetails(System.currentTimeMillis())
        }

        _binding?.apply {
            button.setOnClickListener {
                updateData()
                infoViewModel.infoAboutTravel.let { it1 -> infoViewModel.updateDetails(it1) }
            }

            spinnerHotel.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        itemSelected: View, selectedItemPosition: Int, selectedId: Long
                    ) {
                        infoViewModel.selectedHotelPos = selectedItemPosition
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

            //TODO переустановить напоминалки
            dateOfJourney.setOnClickListener {
                pickDate(TIME_ID)
            }

            dateOfJourneyDest.setOnClickListener {
                pickDate(TIME_DEST_ID)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        portsList =
            (ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        hotelsList =
            (ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        _binding?.apply {
            spinner.adapter = portsList
            spinnerDest.adapter = portsList
            spinnerHotel.adapter = hotelsList
        }
    }

    private fun handleCommand(viewCommand: ViewCommand) {
        if (viewCommand is SetAlarm) setAlarm(viewCommand)
    }

    private fun setAlarm(viewCommand: SetAlarm) {
        setAlarm(viewCommand.intent, viewCommand.time)
    }

    private fun setAlarm(intent: Intent, time: Long) {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            REQUEST_CODE, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC, time, pendingIntent)
        }
    }

    private fun handleState(state: InfoViewState) {
        refresh(state)
        when (state) {
            is InfoViewState.Content -> state.handle()
            is InfoViewState.Error -> state.handle()
        }
    }

    private fun InfoViewState.Content.handle() {
        _binding?.apply {
            with(infoViewModel) {
                dateOfJourney.setText(convertMillisToText(event?.timeInMillis))
                spinner.setSelection(event?.portId.orDefault() - 1)
                flight.setText(event?.flightNum)
                seat.setText(event?.seat)
                route.setText(event?.wayDescription)
                spinnerTime.setSelection(convertHoursToAdapterPosition(event?.hours))
                dateOfJourneyDest.setText(convertMillisToText(event?.timeInMillisDest))
                spinnerDest.setSelection(event?.destPortId.orDefault() - 1)
                flightDest.setText(event?.flightNumFromDest)
                seatDest.setText(event?.seatFromDest)
                routeDest.setText(event?.wayDescriptionFromDest)
                spinnerTimeDest.setSelection(
                    convertHoursToAdapterPosition(event?.hoursFromDest)
                )
                wayToHotel.setText(event?.wayToHotel)

                if (hotels?.isNotEmpty() == true) {
                    val hotel = hotels.single { it.id == event?.hotelId }.title
                    val index = hotels.indexOfFirst { it.title == hotel }

                    if (index != -1) {
                        spinnerHotel.setSelection(index)
                        hotelAddress.text = hotels[index].address
                        hotelPhone.text = hotels[index].phone
                        hotelSubway.text = hotels[index].subway
                        selectedHotelId = hotels[index].id
                    } else {
                        spinnerHotel.setSelection(DEFAULT_POSITION)
                    }
                }
            }

            if (portsList.isEmpty) ports?.forEach { portsList.add(it.name) }
            if (hotelsList.isEmpty) hotels?.forEach { hotelsList.add(it.title) }
        }
    }

    private fun InfoViewState.Error.handle() {
        _binding?.errorPanel?.apply {
            errorIcon.setImageResource(errorModel.icon)
            errorTitle.setText(errorModel.title)
        }
    }

    private fun refresh(state: InfoViewState) {
        _binding?.apply {
            contentPanel.isVisible = state is InfoViewState.Content
            errorPanel.root.isVisible = state is InfoViewState.Error
        }
    }

    private fun updateData() {
        _binding?.apply {
            with(infoViewModel) {
                infoAboutTravel = infoAboutTravel.copyInfoAboutTravel(
                    portId = spinner.selectedItemPosition.orDefault() + 1,
                    flightNum = flight.text.toString(),
                    seat = seat.text.toString(),
                    wayDescription = route.text.toString(),
                    hours = spinnerTime.selectedItemPosition.orDefault().toHours(),
                    destPortId = spinnerDest.selectedItemPosition.orDefault() + 1,
                    flightNumFromDest = flightDest.text.toString(),
                    seatFromDest = seatDest.text.toString(),
                    wayDescriptionFromDest = route.text.toString(),
                    hoursFromDest = spinnerTimeDest.selectedItemPosition.orDefault().toHours(),
                    hotelId = selectedHotelId,
                    wayToHotel = wayToHotel.text.toString()
                )
            }
        }
    }

    private fun pickDate(id: String) {
        val currentDate = infoViewModel.formatter.getCurrentDate()
        DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                infoViewModel.selectedDateTime =
                    infoViewModel.selectedDateTime.copyEditDateTime(
                        year = year, month = monthOfYear, day = dayOfMonth
                    )
                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        infoViewModel.selectedDateTime =
                            infoViewModel.selectedDateTime.copyEditDateTime(
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
        const val REQUEST_CODE = 0
        const val TIME_ID = "time"
        const val TIME_DEST_ID = "timeDest"
        const val DEFAULT_POSITION = 0
    }
}