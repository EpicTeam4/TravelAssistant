package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.travelassistant.R
import com.example.travelassistant.databinding.FragmentFromDestinationBinding
import com.example.travelassistant.features.travelinfo.presentation.ui.InfoViewModel
import com.example.travelassistant.features.travelinfo.presentation.utils.convertLongDateToString
import com.example.travelassistant.features.travelinfo.presentation.utils.toHours
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FromDestinationFragment : DateTime() {

    private val infoViewModel: InfoViewModel by activityViewModels()
    private var _binding: FragmentFromDestinationBinding? = null
    private lateinit var portsList: ArrayAdapter<Any>
    private var fromDestSelectedPort: Int = 0
    private var fromDestSelectedHours: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_from_destination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFromDestinationBinding.bind(view)

        with(_binding) {
            this?.button?.setOnClickListener {
                getSelectedPortId()
                getSelectedTime()

                requireActivity().findNavController(R.id.navHostFragment)
                    .navigate(R.id.action_fromDestinationFragment_to_hotelFragment)
            }
            this?.dateOfJourney?.setOnClickListener {
                pickDate()
                dateOfJourney.setText(convertLongDateToString(timeInMillis))
            }
        }
        initObservers()
        infoViewModel.initPorts()
    }

    override fun onResume() {
        super.onResume()
        with(_binding) {
            this?.spinner?.setSelection(fromDestSelectedPort)
            this?.spinnerTime?.selectedItemPosition?.let { spinnerTime.setSelection(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        portsList = (ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        infoViewModel.allPorts.observe(viewLifecycleOwner) { ports ->
            portsList.clear()
            ports.forEach { portsList.add(it.name) }
            portsList.notifyDataSetChanged()
        }
        _binding?.spinner?.adapter = portsList
    }

    private fun getSelectedPortId(): Int {
        fromDestSelectedPort = _binding?.spinner?.selectedItemPosition!!
        return fromDestSelectedPort
    }

    private fun getSelectedTime(): Long {
        fromDestSelectedHours = _binding?.spinnerTime?.selectedItemPosition!!.toHours()
        return fromDestSelectedHours
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}