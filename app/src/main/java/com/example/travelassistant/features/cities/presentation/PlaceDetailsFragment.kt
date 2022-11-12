package com.example.travelassistant.features.cities.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.travelassistant.databinding.FragmentPlaceDetailsBinding
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaceDetailsFragment : Fragment() {

    private var _binding: FragmentPlaceDetailsBinding? = null
    private val binding get() = _binding!!

    val args: PlaceDetailsFragmentArgs by navArgs()

    private val placeDetailViewModel: PlaceDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeState()
        placeDetailViewModel.sendEvent(PlaceContract.Event.OnViewReady(args.placeId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setPlace(place: PlaceDomain) {
        with(binding) {
            placeDetailsTitle.text = place.title
            placeDetailsDescription.text = place.description
            if (place.images.isNotEmpty()) {
                place.images.first().let {
                    Picasso.get()
                        .load(it.url)
                        .into(placeDetailsImage)
                }
            }
        }
    }

    private fun subscribeState() {
        lifecycleScope.launch {
            placeDetailViewModel.uiState.collect { handleState(it) }
        }
    }

    private fun handleState(state: PlaceContract.State) {
        when (state) {
            is PlaceContract.State.Loading -> {
                binding.progressbar.isVisible = true
                binding.errorPanel.root.isVisible = false
            }
            is PlaceContract.State.Error -> { // todo проверить что работает
                binding.progressbar.isVisible = false
                binding.errorPanel.root.isVisible = true
                binding.errorPanel.apply {
                    errorIcon.setImageResource(state.errorModel.icon)
                    errorTitle.setText(state.errorModel.title)
                }
            }
            is PlaceContract.State.Content -> {
                binding.progressbar.isVisible = false
                binding.errorPanel.root.isVisible = false
                setPlace(state.place)
            }
        }
    }
    
}