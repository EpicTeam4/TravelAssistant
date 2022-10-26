package com.example.travelassistant.features.cities.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.COUNT_OF_CITIES_SCREEN_COLUMNS
import com.example.travelassistant.databinding.FragmentCitiesBinding
import kotlinx.coroutines.launch
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
        citiesAdapter = CitiesRecyclerViewAdapter(mutableListOf()) { cityId ->
            citiesViewModel.sendEvent(
                CitiesContract.Event.OnCityClick(cityId)
            )
        }

        _binding = FragmentCitiesBinding.inflate(inflater, container, false)

        recyclerView = binding.citiesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, COUNT_OF_CITIES_SCREEN_COLUMNS)
        recyclerView.adapter = citiesAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeState()
        subscribeNavigationEvent()
        citiesViewModel.sendEvent(CitiesContract.Event.OnViewReady)
    }

    private fun subscribeState() {
        lifecycleScope.launch {
            citiesViewModel.uiState.collect { handleState(it) }
        }
    }

    private fun subscribeNavigationEvent() {
        lifecycleScope.launch {
            citiesViewModel.navigationEvent.collect { handleNavigationEvent(it) }
        }
    }

    private fun handleNavigationEvent(event: CitiesContract.NavigationEvent) {
        when (event) {
            is CitiesContract.NavigationEvent.NavigateToPlaces -> {
                val action =
                    CitiesFragmentDirections.actionNavigationCitiesToCitiesPlaces(event.cityId)
                requireActivity().findNavController(R.id.navHostFragment) // todo фрагмент не должен ничего знать о навигации
                    .navigate(action)
            }
        }
    }

    private fun handleState(state: CitiesContract.State) {
        when (state) {
            is CitiesContract.State.Loading -> {  // todo проверить что работает
                Log.d("=======", "CitiesContract.State.Loading")
                binding.progressbar.isVisible = true
                binding.citiesRecyclerView.isVisible = false
                binding.errorPanel.root.isVisible = false
            }
            is CitiesContract.State.Error -> { // todo проверить что работает
                binding.progressbar.isVisible = false
                binding.citiesRecyclerView.isVisible = false
                binding.errorPanel.root.isVisible = true
                binding.errorPanel.apply {
                        errorIcon.setImageResource(state.errorModel.icon)
                        errorTitle.setText(state.errorModel.title)
                    }
            }
            is CitiesContract.State.Content -> {
                binding.progressbar.isVisible = false
                binding.citiesRecyclerView.isVisible = true
                binding.errorPanel.root.isVisible = false
                (binding.citiesRecyclerView.adapter as CitiesRecyclerViewAdapter).setCities(state.cities)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}