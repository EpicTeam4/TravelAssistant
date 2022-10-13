package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.DEFAULT_VALUE
import com.example.travelassistant.core.orDefault
import com.example.travelassistant.databinding.FragmentFromDestinationBinding
import com.example.travelassistant.features.travelinfo.presentation.ui.TravelInfoViewState
import com.example.travelassistant.features.travelinfo.presentation.ui.observe
import com.example.travelassistant.features.travelinfo.presentation.utils.toHours
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FromDestinationFragment : BaseFragment() {

    private var _binding: FragmentFromDestinationBinding? = null
    private lateinit var portsList: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_from_destination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFromDestinationBinding.bind(view)

        initObservers()
        observe(infoViewModel.commands, ::handleCommand)
        infoViewModel.loadData(DEFAULT_VALUE)

        val selectedCityId = FromDestinationFragmentArgs.fromBundle(requireArguments()).cityId

        _binding?.apply {
            button.setOnClickListener {
                getSelectedPortId()
                getSelectedTime()

                infoViewModel.openHotelFragment(
                    FromDestinationFragmentDirections.actionFromDestinationFragmentToHotelFragment(
                        selectedCityId
                    )
                )
            }

            dateOfJourney.setOnClickListener {
                pickDate()
                dateOfJourney.setText(infoViewModel.formatter.convertLongDateToString(
                    infoViewModel.tempDate)
                )
                infoViewModel.infoAboutTravel = infoViewModel.infoAboutTravel.copyInfoAboutTravel(
                    timeInMillisDest = infoViewModel.tempDate
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        _binding?.apply {
            spinner.setSelection(infoViewModel.infoAboutTravel.destPortId)
            spinnerTime.selectedItemPosition.let { spinnerTime.setSelection(it) }
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

        infoViewModel.dataState.observe(viewLifecycleOwner, ::handleState)
        _binding?.spinner?.adapter = portsList
    }

    private fun handleState(state: TravelInfoViewState) {
        refresh(state)
        when (state) {
            is TravelInfoViewState.Content -> state.handle()
            is TravelInfoViewState.Error -> state.handle()
        }
    }

    private fun TravelInfoViewState.Content.handle() {
        if (portsList.isEmpty) ports.forEach { portsList.add(it.name) }
    }

    private fun TravelInfoViewState.Error.handle() {
        _binding?.errorPanel?.apply {
            errorIcon.setImageResource(errorModel.icon)
            errorTitle.setText(errorModel.title)
        }
    }

    private fun refresh(state: TravelInfoViewState) {
        _binding?.apply {
            contentPanel.isVisible = state is TravelInfoViewState.Content
            errorPanel.root.isVisible = state is TravelInfoViewState.Error
        }
    }

    private fun getSelectedPortId(): Int {
        infoViewModel.infoAboutTravel = infoViewModel.infoAboutTravel.copyInfoAboutTravel(
                destPortId = _binding?.spinner?.selectedItemPosition.orDefault()
            )
        return infoViewModel.infoAboutTravel.destPortId
    }

    private fun getSelectedTime(): Long {
        infoViewModel.infoAboutTravel = infoViewModel.infoAboutTravel.copyInfoAboutTravel(
                hoursFromDest = _binding?.spinnerTime?.selectedItemPosition.orDefault().toHours()
            )
        return infoViewModel.infoAboutTravel.hoursFromDest
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) requireActivity().onBackPressed()
        return super.onOptionsItemSelected(menuItem)
    }
}