package com.example.travelassistant.features.travelinfo.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.BaseFragment
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.COUNT_OF_COLUMNS
import com.example.travelassistant.databinding.FragmentCitiesListBinding
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.features.travelinfo.presentation.adapters.CityAdapter

/**
 * Фрагмент со списком городов
 *
 * @author Marianne Sabanchieva
 */

class CitiesListFragment : BaseFragment() {

    private val infoViewModel: InfoViewModel by viewModels { factory }
    private var _binding: FragmentCitiesListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var citiesAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cities_list, container, false)

        recyclerView = view.findViewById(R.id.cities) as RecyclerView
        citiesAdapter = CityAdapter(mutableListOf()) { id ->
            onCityClick(id)
        }
        val layoutManager = GridLayoutManager(requireContext(), COUNT_OF_COLUMNS)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = citiesAdapter

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
        //TODO: будет реализован переход к фрагменту
    }
}