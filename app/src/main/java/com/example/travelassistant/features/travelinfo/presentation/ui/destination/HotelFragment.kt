package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.travelassistant.R
import com.example.travelassistant.databinding.FragmentHotelBinding
import com.example.travelassistant.features.travelinfo.presentation.ui.TravelInfoViewModel
import com.example.travelassistant.features.travelinfo.presentation.ui.TravelInfoViewState
import com.example.travelassistant.features.travelinfo.presentation.ui.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelFragment : BaseFragment() {

    private var _binding: FragmentHotelBinding? = null
    private val infoViewModel by activityViewModels<TravelInfoViewModel>()
    private lateinit var hotelsList: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_hotel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHotelBinding.bind(view)

        val selectedCityId = HotelFragmentArgs.fromBundle(requireArguments()).cityId

        infoViewModel.loadHotels(selectedCityId)
        initObservers()
        observe(infoViewModel.commands, ::handleCommand)

        _binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                infoViewModel.selectedHotelPos = selectedItemPosition
                infoViewModel.dataState.observe(viewLifecycleOwner, ::handleState)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        _binding?.apply {
            button.setOnClickListener {
                infoViewModel.openItemsFragment(
                    HotelFragmentDirections.actionHotelFragmentToPersonalItemsFragment()
                )
            }

            swipeRefreshLayout.setOnRefreshListener {
                infoViewModel.loadHotels(selectedCityId)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        _binding?.spinner?.setSelection(infoViewModel.selectedHotelPos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        hotelsList =
            (ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        _binding?.spinner?.adapter = hotelsList
        infoViewModel.dataState.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: TravelInfoViewState) {
        refresh(state)
        when (state) {
            is TravelInfoViewState.Content -> state.handle()
            is TravelInfoViewState.Error -> state.handle()
        }
    }

    private fun TravelInfoViewState.Content.handle() {
        if (hotelsList.isEmpty)
            hotels.forEach { hotel ->
                hotelsList.add(hotel.title.replaceFirstChar { it.uppercaseChar() })
            }

        _binding?.apply {
            if (hotels.isNotEmpty()) {
                val id = hotels[infoViewModel.selectedHotelPos].id
                infoViewModel.infoAboutTravel =
                    infoViewModel.infoAboutTravel.copyInfoAboutTravel(hotelId = id)
                hotelAddress.text = hotels[infoViewModel.selectedHotelPos].address
                hotelPhone.text = hotels[infoViewModel.selectedHotelPos].phone
                hotelWebsite.text = hotels[infoViewModel.selectedHotelPos].subway
            }
        }
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

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) requireActivity().onBackPressed()
        return super.onOptionsItemSelected(menuItem)
    }
}