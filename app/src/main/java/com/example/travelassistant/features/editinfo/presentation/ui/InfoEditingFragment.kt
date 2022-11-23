package com.example.travelassistant.features.editinfo.presentation.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.travelassistant.R
import com.example.travelassistant.TimeNotification
import com.example.travelassistant.core.Constants.ACTION_NAME
import com.example.travelassistant.core.Constants.AIRPORT
import com.example.travelassistant.core.Constants.EMPTY_STRING
import com.example.travelassistant.core.Constants.NOTIFICATION_ID
import com.example.travelassistant.core.Constants.NOTIFICATION_TEXT
import com.example.travelassistant.core.Constants.RAILWAY
import com.example.travelassistant.core.observe
import com.example.travelassistant.core.orDefault
import com.example.travelassistant.databinding.FragmentEditEventBinding
import com.example.travelassistant.core.commands.SetAlarm
import com.example.travelassistant.core.commands.ViewCommand
import com.example.travelassistant.core.utils.toHours
import java.util.Calendar
import java.util.concurrent.TimeUnit

class InfoEditingFragment : Fragment() {

    private lateinit var portsList: ArrayAdapter<String>
    private lateinit var railwayList: ArrayAdapter<String>
    private lateinit var hotelsList: ArrayAdapter<String>
    private lateinit var portsDestList: ArrayAdapter<String>
    private lateinit var railwayDestList: ArrayAdapter<String>
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
            observe(commands, ::handleCommand)
            loadDetails(System.currentTimeMillis())
        }

        _binding?.apply {
            infoViewModel.apply {

                avia.setOnClickListener {
                    spinner.isInvisible = false
                    spinnerRailway.isInvisible = true
                    seat.setHint(R.string.van_seat_num)
                    setPortType(true)
                }

                railway.setOnClickListener {
                    spinner.isInvisible = true
                    spinnerRailway.isInvisible = false
                    seat.setHint(R.string.van_seat_number)
                    setPortType(false)
                }

                aviaDest.setOnClickListener {
                    spinnerDest.isInvisible = false
                    spinnerRailwayDest.isInvisible = true
                    seat.setHint(R.string.van_seat_num)
                    setPortDestType(true)
                }

                railwayDest.setOnClickListener {
                    spinnerDest.isInvisible = true
                    spinnerRailwayDest.isInvisible = false
                    seat.setHint(R.string.van_seat_number)
                    setPortDestType(false)
                }
            }

            button.setOnClickListener {
                updateData()
                infoViewModel.apply {
                    updateDetails(infoAboutTravel)
                    setAlarm()
                    setSecondAlarm()
                }
            }

            calendar.setOnClickListener {
                pickDate(TIME_ID)
            }

            calendarDest.setOnClickListener {
                pickDate(TIME_DEST_ID)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        infoViewModel.selectedHotelPos = DEFAULT_VALUE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        portsList =
            (ArrayAdapter<String>(requireContext(), R.layout.spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        railwayList =
            (ArrayAdapter<String>(requireContext(), R.layout.spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        portsDestList =
            (ArrayAdapter<String>(requireContext(), R.layout.spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        railwayDestList =
            (ArrayAdapter<String>(requireContext(), R.layout.spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        hotelsList =
            (ArrayAdapter<String>(requireContext(), R.layout.spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        _binding?.apply {
            spinner.adapter = portsList
            spinnerRailway.adapter = railwayList
            spinnerDest.adapter = portsDestList
            spinnerRailwayDest.adapter = railwayDestList
        }
    }

    private fun handleCommand(viewCommand: ViewCommand) {
        if (viewCommand is SetAlarm) setAlarm(viewCommand)
    }

    private fun setAlarm(viewCommand: SetAlarm) {
        setAlarm(viewCommand.id, viewCommand.time, viewCommand.alarmText)
    }

    private fun setAlarm(id: Int, time: Long, alarmText: String) {
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag(id.toString())

        val data = Data.Builder()
            .putLong(ACTION_NAME, time)
            .putInt(NOTIFICATION_ID, id)
            .putString(NOTIFICATION_TEXT, alarmText)
            .build()

        val myWorkRequest = OneTimeWorkRequest.Builder(TimeNotification::class.java)
            .setInputData(data)
            .addTag(id.toString())
            .setInitialDelay(time, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
    }

    private fun handleState(state: InfoViewState) {
        refresh(state)
        when (state) {
            is InfoViewState.Loading -> refresh(state)
            is InfoViewState.Content -> state.handle()
            is InfoViewState.Error -> state.handle()
        }
    }

    private fun InfoViewState.Content.handle() {
        _binding?.apply {
            with(infoViewModel) {
                dateOfJourney.setText(convertMillisToText(infoAboutTravel.timeInMillis))
                if (infoAboutTravel.portType == RAILWAY) {
                    railway.callOnClick()
                    if (infoAboutTravel.portId <= railways.size) {
                        spinnerRailway.setSelection(infoAboutTravel.portId)
                    } else {
                        spinnerRailway.setSelection(DEFAULT_POSITION)
                    }
                } else {
                    if (infoAboutTravel.portId <= airports.size) {
                        spinner.setSelection(infoAboutTravel.portId)
                    } else {
                        spinner.setSelection(DEFAULT_POSITION)
                    }
                }
                flight.setText(event?.flightNum)
                seat.setText(event?.seat)
                route.setText(event?.wayDescription)
                spinnerTime.setSelection(convertHoursToAdapterPosition(event?.hours))
                dateOfJourneyDest.setText(convertMillisToText(infoAboutTravel.timeInMillisDest))
                if (infoAboutTravel.destPortType == RAILWAY) {
                    railway.callOnClick()
                    if (infoAboutTravel.destPortId <= railwaysDest.size) {
                        spinnerRailwayDest.setSelection(infoAboutTravel.destPortId)
                    } else {
                        spinnerRailwayDest.setSelection(DEFAULT_POSITION)
                    }
                } else {
                    if (infoAboutTravel.destPortId <= airportsDest.size) {
                        spinnerDest.setSelection(infoAboutTravel.destPortId)
                    } else {
                        spinnerDest.setSelection(DEFAULT_POSITION)
                    }
                }
                flightDest.setText(event?.flightNumFromDest)
                seatDest.setText(event?.seatFromDest)
                routeDest.setText(event?.wayDescriptionFromDest)
                spinnerTimeDest.setSelection(convertHoursToAdapterPosition(event?.hoursFromDest))
                titleHotel.setText(event?.hotelName)
                hotelAddress.setText(event?.hotelAddress)
                hotelPhone.setText(event?.hotelPhone)
                hotelSubway.setText(event?.hotelSubway)
                wayToHotel.setText(event?.wayToHotel)

                if (dateOfJourney.text.toString() != EMPTY_STRING && infoAboutTravel.hours.toInt() != 0) {
                    notify.isActivated
                    notify.setImageResource(R.drawable.alarm_on)
                } else {
                    notify.isActivated = false
                    notify.setImageResource(R.drawable.alarm_off)
                }
                if (dateOfJourneyDest.text.toString() != EMPTY_STRING && infoAboutTravel.hoursFromDest.toInt() != 0) {
                    notifyDest.isActivated
                    notifyDest.setImageResource(R.drawable.alarm_on)
                } else {
                    notifyDest.isActivated = false
                    notifyDest.setImageResource(R.drawable.alarm_off)
                }
            }

            if (portsList.isEmpty) airports.forEach { portsList.add(it.name) }
            if (railwayList.isEmpty) railways.forEach { railwayList.add(it.name) }
            if (portsDestList.isEmpty) airportsDest.forEach { portsDestList.add(it.name) }
            if (railwayDestList.isEmpty) railwaysDest.forEach { railwayDestList.add(it.name) }
            if (hotelsList.isEmpty) hotels.forEach { hotelsList.add(it.title) }
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
            progressBar.isVisible = state is InfoViewState.Loading
            contentPanel.isVisible = state is InfoViewState.Content
            errorPanel.root.isVisible = state is InfoViewState.Error
        }
    }

    private fun updateData() {
        _binding?.apply {
            with(infoViewModel) {
                infoAboutTravel = infoAboutTravel.copyInfoAboutTravel(
                    portId = when (infoAboutTravel.portType) {
                        EMPTY_STRING -> {
                            setPortType(true)
                            spinner.selectedItemPosition.orDefault()
                        }
                        AIRPORT -> {
                            spinner.selectedItemPosition.orDefault()
                        }
                        RAILWAY -> {
                            spinnerRailway.selectedItemPosition.orDefault()
                        }
                        else -> {
                            spinner.selectedItemPosition.orDefault()
                        }
                    },
                    flightNum = flight.text.toString(),
                    seat = seat.text.toString(),
                    wayDescription = route.text.toString(),
                    hours = spinnerTime.selectedItemPosition.orDefault().toHours(),
                    destPortId = when (infoAboutTravel.destPortType) {
                        EMPTY_STRING -> {
                            setPortDestType(true)
                            spinnerDest.selectedItemPosition.orDefault()
                        }
                        AIRPORT -> {
                            spinnerDest.selectedItemPosition.orDefault()
                        }
                        RAILWAY -> {
                            spinnerRailwayDest.selectedItemPosition.orDefault()
                        }
                        else -> {
                            spinnerDest.selectedItemPosition.orDefault()
                        }
                    },
                    flightNumFromDest = flightDest.text.toString(),
                    seatFromDest = seatDest.text.toString(),
                    wayDescriptionFromDest = route.text.toString(),
                    hoursFromDest = spinnerTimeDest.selectedItemPosition.orDefault().toHours(),
                    hotelId = selectedHotelId,
                    hotelName = titleHotel.text.toString(),
                    hotelAddress = hotelAddress.text.toString(),
                    hotelPhone = hotelPhone.text.toString(),
                    hotelSubway = hotelSubway.text.toString(),
                    wayToHotel = wayToHotel.text.toString()
                )

                if (dateOfJourney.text.toString() != EMPTY_STRING && infoAboutTravel.hours.toInt() != 0) {
                    notify.isActivated
                    notify.setImageResource(R.drawable.alarm_on)
                } else {
                    notify.isActivated = false
                    notify.setImageResource(R.drawable.alarm_off)
                }
                if (dateOfJourneyDest.text.toString() != EMPTY_STRING && infoAboutTravel.hoursFromDest.toInt() != 0) {
                    notifyDest.isActivated
                    notifyDest.setImageResource(R.drawable.alarm_on)
                } else {
                    notifyDest.isActivated = false
                    notifyDest.setImageResource(R.drawable.alarm_off)
                }
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
                            with(infoViewModel) {
                                getDateInMillis()
                                dataState.observe(viewLifecycleOwner, ::handleState)
                            }
                        } else {
                            with(infoViewModel) {
                                getDateDestInMillis()
                                dataState.observe(viewLifecycleOwner, ::handleState)
                            }
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
        const val DEFAULT_POSITION = 0
        const val DEFAULT_VALUE = -1
    }
}