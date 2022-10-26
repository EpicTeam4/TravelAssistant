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
import com.example.travelassistant.core.observe
import com.example.travelassistant.databinding.FragmentCitiesBinding
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

    private var _binding: FragmentCitiesBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var citiesAdapter: CityAdapter
    private val infoViewModel: TravelInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cities, container, false)

        citiesAdapter = CityAdapter(mutableListOf()) { id -> onCityClick(id) }
        recyclerView = (view.findViewById(R.id.cities_recycler_view) as RecyclerView).apply {
            layoutManager = GridLayoutManager(requireContext(), COUNT_OF_CITY_CARD_COLUMNS)
            adapter = citiesAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCitiesBinding.bind(view)

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
            citiesRecyclerView.isVisible = state is TravelInfoViewState.Content
            errorPanel.root.isVisible = state is TravelInfoViewState.Error
        }
    }

    private fun onCityClick(id: Long) {
        infoViewModel.infoAboutTravel = infoViewModel.infoAboutTravel.copyInfoAboutTravel(city_id = id)
        infoViewModel.openToDestination()
    }
}