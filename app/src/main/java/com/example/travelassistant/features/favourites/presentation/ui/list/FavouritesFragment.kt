package com.example.travelassistant.features.favourites.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.commands.GoToFragmentAndSendSafeArgs
import com.example.travelassistant.core.commands.ViewCommand
import com.example.travelassistant.core.observe
import com.example.travelassistant.core.toNavigateDirection
import com.example.travelassistant.databinding.FragmentFavouritesBinding
import com.example.travelassistant.features.favourites.presentation.adapters.CityWithFavouritePlacesAdapter
import com.example.travelassistant.features.favourites.presentation.ui.SightsViewState

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var sightsAdapter: CityWithFavouritePlacesAdapter
    private val sightsViewModel: FavouritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerView = view.findViewById(R.id.favourite_sights_main) as RecyclerView
        sightsAdapter = CityWithFavouritePlacesAdapter(mutableListOf(), mutableListOf(), { id ->
            deleteSights(id)
        }) { id -> onSightsClick(id) }
        recyclerView.adapter = sightsAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavouritesBinding.bind(view)

        sightsViewModel.dataState.observe(viewLifecycleOwner, ::handleState)
        observe(sightsViewModel.commands, ::handleCommand)
        sightsViewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleCommand(viewCommand: ViewCommand) {
        if (viewCommand is GoToFragmentAndSendSafeArgs) goToFragment(viewCommand)
    }

    private fun goToFragment(viewCommand: GoToFragmentAndSendSafeArgs) {
        requireActivity().toNavigateDirection(viewCommand.nav)
    }

    private fun handleState(state: SightsViewState) {
        refresh(state)
        when (state) {
            is SightsViewState.Content -> state.handle()
            is SightsViewState.Error -> state.handle()
        }
    }

    private fun SightsViewState.Content.handle() {
        sightsAdapter.setCitiesAndSights(cities, sights)
    }

    private fun SightsViewState.Error.handle() {
        _binding?.errorPanel?.apply {
            errorIcon.setImageResource(errorModel.icon)
            errorTitle.setText(errorModel.title)
        }
    }

    private fun refresh(state: SightsViewState) {
        _binding?.apply {
            favouriteSightsMain.isVisible = state is SightsViewState.Content
            errorPanel.root.isVisible = state is SightsViewState.Error
        }
    }

    private fun deleteSights(id: Int) {
        sightsViewModel.deleteSights(id)
    }

    private fun onSightsClick(id: Int) {
        sightsViewModel.openDetails(id)
    }
}