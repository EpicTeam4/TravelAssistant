package com.example.travelassistant.features.cities.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.COUNT_OF_CITIES_SCREEN_COLUMNS
import com.example.travelassistant.databinding.FragmentCitiesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var citiesAdapter: CitiesRecyclerViewAdapter

    private val citiesViewModel: CitiesViewModel by viewModel()
    private val placesViewModel: PlacesViewModel by sharedViewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        citiesAdapter = CitiesRecyclerViewAdapter(mutableListOf()) { id -> onCityClick(id) }

        _binding = FragmentCitiesBinding.inflate(inflater, container, false)

        recyclerView = binding.citiesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, COUNT_OF_CITIES_SCREEN_COLUMNS)
        recyclerView.adapter = citiesAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        citiesViewModel.cities.observe(viewLifecycleOwner, citiesAdapter::setCities)
        citiesViewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCityClick(cityId: String) {
        placesViewModel.selectCity(cityId)
        requireActivity().findNavController(R.id.navHostFragment)
            .navigate(R.id.action_navigation_cities_to_cities_places)
    }

}