package com.example.travelassistant.features.cities.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.databinding.FragmentCitiesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var citiesAdapter: CitiesRecyclerViewAdapter

    private val citiesViewModel: CitiesViewModel by viewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        citiesAdapter = CitiesRecyclerViewAdapter(mutableListOf())

        _binding = FragmentCitiesBinding.inflate(inflater, container, false)

        recyclerView = binding.citiesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
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

}