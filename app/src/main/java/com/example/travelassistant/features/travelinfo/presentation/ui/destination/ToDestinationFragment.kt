package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.AIRPORT
import com.example.travelassistant.core.Constants.EMPTY_STRING
import com.example.travelassistant.core.Constants.RAILWAY
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.observe
import com.example.travelassistant.core.orDefault
import com.example.travelassistant.core.utils.toHours
import com.example.travelassistant.core.utils.toPosition
import com.example.travelassistant.databinding.FragmentToDestinationBinding
import com.example.travelassistant.features.travelinfo.presentation.ui.TravelInfoViewModel
import com.example.travelassistant.features.travelinfo.presentation.ui.TravelInfoViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDestinationFragment : BaseFragment() {

    private var _binding: FragmentToDestinationBinding? = null
    private lateinit var portsList: ArrayAdapter<String>
    private lateinit var railwayList: ArrayAdapter<String>
    private val infoViewModel: TravelInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_to_destination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentToDestinationBinding.bind(view)

        initObservers()
        observe(infoViewModel.commands, ::handleCommand)
        infoViewModel.loadData()

        _binding?.apply {
            with(infoViewModel) {

                avia.setOnClickListener{
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

                button.setOnClickListener {
                    getSelectedTime()
                    setData()

                    when (infoAboutTravel.portType) {
                        EMPTY_STRING -> {
                            setPortType(true)
                            getSelectedPortId()
                        }
                        AIRPORT -> {
                            getSelectedPortId()
                        }
                        RAILWAY -> {
                            getSelectedRailwayPortId()
                        }
                    }

                    openFromDestination()
                }
            }

            calendar.setOnClickListener {
                pickDate(TIME_ID)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        portsList = (ArrayAdapter<String>(requireContext(), R.layout.spinner_item)).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        railwayList = (ArrayAdapter<String>(requireContext(), R.layout.spinner_item)).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        infoViewModel.dataState.observe(viewLifecycleOwner, ::handleState)
        _binding?.spinner?.adapter = portsList
        _binding?.spinnerRailway?.adapter = railwayList
    }

    private fun handleState(state: TravelInfoViewState) {
        refresh(state)
        when (state) {
            is TravelInfoViewState.Loading -> refresh(state)
            is TravelInfoViewState.Content -> state.handle()
            is TravelInfoViewState.Error -> state.handle()
        }
    }

    private fun TravelInfoViewState.Content.handle() {
        if (portsList.isEmpty) airports.forEach { portsList.add(it.name) }
        if (railwayList.isEmpty) railways.forEach { railwayList.add(it.name) }

        _binding?.apply {
            with(infoViewModel) {
                dateOfJourney.setText(getDateTime())

                if (infoAboutTravel.portType == RAILWAY) {
                    avia.isChecked = false
                    railway.callOnClick()
                    spinnerRailway.setSelection(infoAboutTravel.portId)
                } else {
                    spinner.setSelection(infoAboutTravel.portId)
                }
                spinnerTime.setSelection(getPosition().toPosition())
            }
        }
        changeIcon()
    }

    private fun TravelInfoViewState.Error.handle() {
        _binding?.errorPanel?.apply {
            errorIcon.setImageResource(errorModel.icon)
            errorTitle.setText(errorModel.title)
        }
    }

    private fun refresh(state: TravelInfoViewState) {
        _binding?.apply {
            progressBar.isVisible = state is TravelInfoViewState.Loading
            contentPanel.isVisible = state is TravelInfoViewState.Content
            errorPanel.root.isVisible = state is TravelInfoViewState.Error
        }
    }

    private fun getSelectedPortId() {
        infoViewModel.infoAboutTravel = infoViewModel.infoAboutTravel.copyInfoAboutTravel(
            portId = _binding?.spinner?.selectedItemPosition.orDefault()
        )
    }

    private fun getSelectedRailwayPortId() {
        infoViewModel.infoAboutTravel = infoViewModel.infoAboutTravel.copyInfoAboutTravel(
            portId = _binding?.spinnerRailway?.selectedItemPosition.orDefault()
        )
    }

    private fun getSelectedTime() {
        infoViewModel.infoAboutTravel = infoViewModel.infoAboutTravel.copyInfoAboutTravel(
            hours = _binding?.spinnerTime?.selectedItemPosition.orDefault().toHours()
        )
    }

    private fun setData() {
        _binding?.apply {
            infoViewModel.infoAboutTravel = infoViewModel.infoAboutTravel.copyInfoAboutTravel(
                flightNum = flight.text.toString(),
                seat = seat.text.toString(),
                wayDescription = route.text.toString()
            )
        }
    }

    private fun changeIcon() {
        _binding?.apply {
            infoViewModel.apply {
                if (dateOfJourney.text.toString() != EMPTY_STRING && infoAboutTravel.hours.toInt() != 0) {
                    notify.isActivated
                    notify.setImageResource(R.drawable.alarm_on)
                } else {
                    notify.isActivated = false
                    notify.setImageResource(R.drawable.alarm_off)
                }
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
            infoViewModel.infoAboutTravel = InfoAboutTravel()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}