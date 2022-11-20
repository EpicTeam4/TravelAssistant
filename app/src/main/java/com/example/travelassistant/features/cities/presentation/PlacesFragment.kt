package com.example.travelassistant.features.cities.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.databinding.FragmentPlacesBinding
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlacesFragment : Fragment() {
    // todo подгрузка новых элементов при перемотке до конца
    // todo где то вывести название текущего города
    // todo значок в навигации не подсвечивается на этом экране

    private var _binding: FragmentPlacesBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var placesAdapter: PlacesRecyclerViewAdapter

    val args: PlacesFragmentArgs by navArgs()

    private val placesViewModel: PlacesViewModel by viewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        placesAdapter =
            PlacesRecyclerViewAdapter(mutableListOf(),
                onItemClicked = { placeId: String ->
                    placesViewModel.sendEvent(
                        PlacesContract.Event.OnPlaceClick(placeId)
                    )
                },
                onAddPlaceToFavoritesClicked = { place: PlaceDomain ->
                    placesViewModel.sendEvent(
                        PlacesContract.Event.AddPlaceToFavoritesClick(place)
                    )
                })

        _binding = FragmentPlacesBinding.inflate(inflater, container, false)

        recyclerView = binding.placesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = placesAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeState()
        subscribeNavigationEvent()
        placesViewModel.sendEvent(PlacesContract.Event.OnViewReady(args.cityId))
    }

    private fun subscribeState() {
        lifecycleScope.launch {
            placesViewModel.uiState.collect { handleState(it) }
        }
    }

    private fun subscribeNavigationEvent() {
        lifecycleScope.launch {
            placesViewModel.navigationEvent.collect { handleNavigationEvent(it) }
        }
    }

    private fun handleNavigationEvent(event: PlacesContract.NavigationEvent) {
        when (event) {
            is PlacesContract.NavigationEvent.NavigateToPlace -> {
                val action =
                    PlacesFragmentDirections.actionNavigationCitiesPlacesToPlaceDetails(event.placeId)
                requireActivity().findNavController(R.id.navHostFragment) // todo фрагмент не должен ничего знать о навигации
                    .navigate(action)
            }
        }
    }

    private fun handleState(state: PlacesContract.State) {
        when (state) {
            is PlacesContract.State.Loading -> {
                binding.progressbar.isVisible = true
                binding.placesRecyclerView.isVisible = false
                binding.errorPanel.root.isVisible = false
            }
            is PlacesContract.State.Error -> {
                binding.progressbar.isVisible = false
                binding.placesRecyclerView.isVisible = false
                binding.errorPanel.root.isVisible = true
                binding.errorPanel.apply {
                    errorIcon.setImageResource(state.errorModel.icon)
                    errorTitle.setText(state.errorModel.title)
                }
            }
            is PlacesContract.State.Content -> {
                binding.progressbar.isVisible = false
                binding.placesRecyclerView.isVisible = true
                binding.errorPanel.root.isVisible = false
                (binding.placesRecyclerView.adapter as PlacesRecyclerViewAdapter).setPlaces(state.places)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}