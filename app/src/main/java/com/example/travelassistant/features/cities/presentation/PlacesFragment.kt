package com.example.travelassistant.features.cities.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.databinding.FragmentPlacesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PlacesFragment : Fragment() {

    private var _binding: FragmentPlacesBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var placesAdapter: PlacesRecyclerViewAdapter

    private val placesViewModel: PlacesViewModel by sharedViewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        placesAdapter = PlacesRecyclerViewAdapter(mutableListOf())

        _binding = FragmentPlacesBinding.inflate(inflater, container, false)

        recyclerView = binding.placesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = placesAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placesViewModel.places.observe(viewLifecycleOwner, placesAdapter::setPlaces)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}