package com.example.travelassistant.features.favourites.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.databinding.FragmentPlaceDetailsBinding
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.example.travelassistant.features.cities.presentation.PlaceDetailsFragmentArgs
import com.example.travelassistant.features.favourites.presentation.adapters.ImagesAdapter

class FavouritesDetailsFragment : Fragment() {

    private var _binding: FragmentPlaceDetailsBinding? = null
    private val args: PlaceDetailsFragmentArgs by navArgs()
    private val sightsViewModel: SightsViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_place_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPlaceDetailsBinding.bind(view)

        imagesAdapter = ImagesAdapter(mutableListOf())
        recyclerView = (view.findViewById(R.id.place_details_images_recycler_view) as RecyclerView)
            .apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = imagesAdapter
            }

        sightsViewModel.dataState.observe(viewLifecycleOwner, ::getDetails)
        sightsViewModel.loadSights(args.placeId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDetails(details: PlaceDomain) {
        _binding?.apply {
            progressbar.isVisible = false
            with(details) {
                placeDetailsTitle.text = title
                placeDetailsDescription.text = description
                adressValue.text = address
                timeTableValue.text = timeTable
                imgFavourite.isChecked = true
                imgFavourite.setOnClickListener {
                    sightsViewModel.deleteSights(this)
                }
                imagesAdapter.setImages(images.map { it.url })
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) requireActivity().onBackPressed()
        return super.onOptionsItemSelected(menuItem)
    }

}