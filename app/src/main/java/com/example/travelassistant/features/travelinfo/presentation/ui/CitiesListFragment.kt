package com.example.travelassistant.features.travelinfo.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.COUNT_OF_CITY_CARD_COLUMNS
import com.example.travelassistant.databinding.FragmentCitiesListBinding
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.travelinfo.presentation.adapters.CityAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент со списком городов
 *
 * @author Marianne Sabanchieva
 */

@AndroidEntryPoint
class CitiesListFragment : Fragment() {

    private val infoViewModel: InfoViewModel by activityViewModels()
    private var _binding: FragmentCitiesListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var citiesAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cities_list, container, false)

        citiesAdapter = CityAdapter(mutableListOf()) { id ->
            onCityClick(id)
        }
        recyclerView = (view.findViewById(R.id.cities) as RecyclerView).apply {
            layoutManager = GridLayoutManager(requireContext(), COUNT_OF_CITY_CARD_COLUMNS)
            adapter = citiesAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCitiesListBinding.bind(view)

        initObservers()
        infoViewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        infoViewModel.allCities.observe(viewLifecycleOwner, ::setCities)
    }

    private fun setCities(cities: List<City>) {
        citiesAdapter.setCities(cities)
    }

    private fun onCityClick(id: Long) {
        val bundle = bundleOf("id" to id)
        requireActivity().findNavController(R.id.navHostFragment)
            .navigate(R.id.action_navigation_home_to_toDestinationFragment)
    }
}