package com.example.travelassistant.features.editinfo.presentation.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.travelassistant.R
import com.example.travelassistant.TimeNotification
import com.example.travelassistant.core.Constants.ACTION_NAME
import com.example.travelassistant.core.Constants.NOTIFICATION_ID
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
            observe(commands, ::handleCommand)
            loadDetails(System.currentTimeMillis())
        }

        _binding?.apply {
            button.setOnClickListener {
                updateData()
                infoViewModel.apply {
                    updateDetails(infoAboutTravel)
                    setAlarm()
                    setSecondAlarm()
                }
            }

            spinnerHotel.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        itemSelected: View, selectedItemPosition: Int, selectedId: Long
                    ) {
                        infoViewModel.selectedHotelPos = selectedItemPosition
                        infoViewModel.dataState.observe(viewLifecycleOwner, ::handleState)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

            dateOfJourney.setOnClickListener {
                pickDate(TIME_ID)
            }

            dateOfJourneyDest.setOnClickListener {
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
        setAlarm(viewCommand.id, viewCommand.time)
    }

    private fun setAlarm(id: Int, time: Long) {
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag(id.toString())

        val data = Data.Builder()
            .putLong(ACTION_NAME, time)
            .putInt(NOTIFICATION_ID, id)
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
                spinner.setSelection(event?.portId.orDefault() - 1)
                flight.setText(event?.flightNum)
                seat.setText(event?.seat)
                route.setText(event?.wayDescription)
                spinnerTime.setSelection(convertHoursToAdapterPosition(event?.hours))
                dateOfJourneyDest.setText(convertMillisToText(infoAboutTravel.timeInMillisDest))
                spinnerDest.setSelection(event?.destPortId.orDefault() - 1)
                flightDest.setText(event?.flightNumFromDest)
                seatDest.setText(event?.seatFromDest)
                routeDest.setText(event?.wayDescriptionFromDest)
                spinnerTimeDest.setSelection(convertHoursToAdapterPosition(event?.hoursFromDest))
                wayToHotel.setText(event?.wayToHotel)

                if (hotels?.isNotEmpty() == true) {
                    val hotel = hotels.single { it.id == event?.hotelId }.title
                    val index = hotels.indexOfFirst { it.title == hotel }

                    if (index != DEFAULT_VALUE) {
                        if (selectedHotelPos == DEFAULT_VALUE) {
                            spinnerHotel.setSelection(index)
                            hotelAddress.text = hotels[index].address
                            hotelPhone.text = hotels[index].phone
                            hotelSubway.text = hotels[index].subway
                            selectedHotelId = hotels[index].id
                        } else {
                            spinnerHotel.setSelection(selectedHotelPos)
                            hotelAddress.text = hotels[selectedHotelPos].address
                            hotelPhone.text = hotels[selectedHotelPos].phone
                            hotelSubway.text = hotels[selectedHotelPos].subway
                            selectedHotelId = hotels[selectedHotelPos].id
                        }
                    } else {
                        if (selectedHotelPos == DEFAULT_VALUE) {
                            spinnerHotel.setSelection(DEFAULT_POSITION)
                            hotelAddress.text = hotels[DEFAULT_POSITION].address
                            hotelPhone.text = hotels[DEFAULT_POSITION].phone
                            hotelSubway.text = hotels[DEFAULT_POSITION].subway
                            selectedHotelId = hotels[DEFAULT_POSITION].id
                        } else {
                            spinnerHotel.setSelection(selectedHotelPos)
                            hotelAddress.text = hotels[selectedHotelPos].address
                            hotelPhone.text = hotels[selectedHotelPos].phone
                            hotelSubway.text = hotels[selectedHotelPos].subway
                            selectedHotelId = hotels[selectedHotelPos].id
                        }
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
            progressBar.isVisible = state is InfoViewState.Loading
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