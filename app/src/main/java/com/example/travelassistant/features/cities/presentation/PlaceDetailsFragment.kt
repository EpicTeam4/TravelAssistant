package com.example.travelassistant.features.cities.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.databinding.FragmentPlaceDetailsBinding
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaceDetailsFragment : Fragment() {

    private var _binding: FragmentPlaceDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesAdapter: PlaceDetailImagesRecyclerViewAdapter

    val args: PlaceDetailsFragmentArgs by navArgs()

    private val placeDetailViewModel: PlaceDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)
        imagesAdapter = PlaceDetailImagesRecyclerViewAdapter(mutableListOf())

        recyclerView = binding.placeDetailsImagesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = imagesAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeState()

        binding.imgFavourite.setOnClickListener {
            placeDetailViewModel.sendEvent(PlaceContract.Event.AddPlaceToFavoritesClick)
        }

        placeDetailViewModel.sendEvent(PlaceContract.Event.OnViewReady(args.placeId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                binding.placeDetailsImagesRecyclerView.isVisible = true
            }
            is PlaceContract.State.Error -> {
                binding.progressbar.isVisible = false
                binding.placeDetailsImagesRecyclerView.isVisible = false
                binding.errorPanel.root.isVisible = true
                binding.errorPanel.apply {
                    errorIcon.setImageResource(state.errorModel.icon)
                    errorTitle.setText(state.errorModel.title)
                }
            }
            is PlaceContract.State.Content -> {
                binding.progressbar.isVisible = false
                binding.errorPanel.root.isVisible = false
                binding.placeDetailsImagesRecyclerView.isVisible = true
                setPlace(state.place)
            }
        }
    }

    private fun setPlace(place: PlaceDomain) {
        with(binding) {
            placeDetailsTitle.text = place.title
            placeDetailsDescription.text = place.description
            adressValue.text = place.address
            timeTableValue.text = place.timeTable
            imgFavourite.isChecked = place.isUserFavorite
            imagesAdapter.setUrls(place.images.map { i -> i.url })
        }
    }

}