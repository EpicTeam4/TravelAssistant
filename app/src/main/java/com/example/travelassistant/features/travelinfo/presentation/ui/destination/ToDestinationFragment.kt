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
import com.example.travelassistant.databinding.FragmentToDestinationBinding
import com.example.travelassistant.features.travelinfo.presentation.ui.InfoViewModel
import com.example.travelassistant.features.travelinfo.presentation.utils.convertLongDateToString
import com.example.travelassistant.features.travelinfo.presentation.utils.toHours
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDestinationFragment : DateTime() {

    private val infoViewModel: InfoViewModel by activityViewModels()
    private var _binding: FragmentToDestinationBinding? = null
    private lateinit var portsList: ArrayAdapter<Any>
    private var selectedPort: Int = 0
    private var selectedHours: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_to_destination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentToDestinationBinding.bind(view)

        with(_binding) {
            this?.button?.setOnClickListener {
                getSelectedPortId()
                getSelectedTime()

                val arguments = Bundle()
                arguments.putLong("datetime", timeInMillis)
                arguments.putInt("port", selectedPort)
                arguments.putString("seat", seat.text.toString())

                requireActivity().findNavController(R.id.navHostFragment)
                    .navigate(R.id.action_toDestinationFragment_to_fromDestinationFragment, arguments)
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
            this?.spinner?.setSelection(selectedPort)
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
        selectedPort = _binding?.spinner?.selectedItemPosition!!
        return selectedPort
    }

    private fun getSelectedTime(): Long {
        selectedHours = _binding?.spinnerTime?.selectedItemPosition!!.toHours()
        return selectedHours
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}