package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.travelassistant.R
import com.example.travelassistant.core.Navigator
import com.example.travelassistant.core.orDefault
import com.example.travelassistant.databinding.FragmentToDestinationBinding
import com.example.travelassistant.features.travelinfo.presentation.model.PortSpinnerPosition
import com.example.travelassistant.features.travelinfo.presentation.model.SelectedHour
import com.example.travelassistant.features.travelinfo.presentation.ui.InfoViewModel
import com.example.travelassistant.features.travelinfo.presentation.utils.DateTimeFormatter
import com.example.travelassistant.features.travelinfo.presentation.utils.toHours
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDestinationFragment : DateTimePickerFragment() {

    private val infoViewModel: InfoViewModel by activityViewModels()
    private var _binding: FragmentToDestinationBinding? = null
    private lateinit var portsList: ArrayAdapter<Any>
    private val navigator = Navigator
    private var selectedPort = PortSpinnerPosition()
    private var selectedHours = SelectedHour()
    private val formatter = DateTimeFormatter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_to_destination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentToDestinationBinding.bind(view)

        val selectedCityId = arguments?.getLong(SELECTED_CITY_ID)

        with(_binding) {
            this?.button?.setOnClickListener {
                getSelectedPortId()
                getSelectedTime()

                navigator.navigateToDestinationFragment(requireActivity(), getBundle(selectedCityId))
            }
            this?.dateOfJourney?.setOnClickListener {
                pickDate()
                dateOfJourney.setText(formatter.convertLongDateToString(selectedDateTime.timeInMillis))
            }
        }
        initObservers()
        infoViewModel.initPorts()
    }

    override fun onResume() {
        super.onResume()
        with(_binding) {
            this?.spinner?.setSelection(selectedPort.pos)
            this?.spinnerTime?.selectedItemPosition?.let { spinnerTime.setSelection(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        portsList = (ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        infoViewModel.allPorts.observe(viewLifecycleOwner) { ports ->
            portsList.clear()
            ports.forEach { portsList.add(it.name) }
            portsList.notifyDataSetChanged()
        }
        _binding?.spinner?.adapter = portsList
    }

    private fun getSelectedPortId(): Int {
        selectedPort = selectedPort.copyPortPosition(pos = _binding?.spinner?.selectedItemPosition.orDefault())
        return selectedPort.pos
    }

    private fun getSelectedTime(): Long {
        selectedHours = selectedHours.copySelectedHour(hours = _binding?.spinnerTime?.selectedItemPosition.orDefault().toHours())
        return selectedHours.hours
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    companion object {
        private const val SELECTED_CITY_ID = "id"

        fun getBundle(id: Long?): Bundle {
            val arguments = Bundle().apply {
                if (id != null) {
                    putLong(SELECTED_CITY_ID, id)
                }
            }
            return arguments
        }
    }
}