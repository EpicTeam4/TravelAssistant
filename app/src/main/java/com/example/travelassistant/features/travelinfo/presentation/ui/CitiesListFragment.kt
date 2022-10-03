package com.example.travelassistant.features.travelinfo.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.COUNT_OF_CITY_CARD_COLUMNS
import com.example.travelassistant.databinding.FragmentCitiesListBinding
import com.example.travelassistant.features.travelinfo.presentation.adapters.CityAdapter
import com.example.travelassistant.features.travelinfo.presentation.ui.destination.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент со списком городов
 *
 * @author Marianne Sabanchieva
 */

@AndroidEntryPoint
class CitiesListFragment : BaseFragment() {

    private val infoViewModel: TravelInfoViewModel by activityViewModels()
    private var _binding: FragmentCitiesListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var citiesAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cities_list, container, false)

        citiesAdapter = CityAdapter(mutableListOf()) { id -> onCityClick(id) }
        recyclerView = (view.findViewById(R.id.cities) as RecyclerView).apply {
            layoutManager = GridLayoutManager(requireContext(), COUNT_OF_CITY_CARD_COLUMNS)
            adapter = citiesAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCitiesListBinding.bind(view)

        infoViewModel.dataState.observe(viewLifecycleOwner, ::handleState)
        observe(infoViewModel.commands, ::handleCommand)
        infoViewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(state: TravelInfoViewState) {
        refresh(state)
        when (state) {
            is TravelInfoViewState.Content -> state.handle()
            is TravelInfoViewState.Error -> state.handle()
        }
    }

    private fun TravelInfoViewState.Content.handle() {
        citiesAdapter.setCities(cities)
    }

    private fun TravelInfoViewState.Error.handle() {
        _binding?.errorPanel?.apply {
            errorIcon.setImageResource(errorModel.icon)
            errorTitle.setText(errorModel.title)
        }
    }

    private fun refresh(state: TravelInfoViewState) {
        _binding?.apply {
            cities.isVisible = state is TravelInfoViewState.Content
            errorPanel.root.isVisible = state is TravelInfoViewState.Error
        }
    }

    private fun onCityClick(id: Long) {
        infoViewModel.openToDestination(
            CitiesListFragmentDirections.actionNavigationHomeToToDestinationFragment(id)
        )
    }
}