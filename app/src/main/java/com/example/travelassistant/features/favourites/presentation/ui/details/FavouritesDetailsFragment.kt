package com.example.travelassistant.features.favourites.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.travelassistant.R
import com.example.travelassistant.core.domain.entity.Sights
import com.example.travelassistant.databinding.FragmentFavouritesDetailsBinding
import com.squareup.picasso.Picasso

class FavouritesDetailsFragment : Fragment() {

    private var _binding: FragmentFavouritesDetailsBinding? = null
    private val args: FavouritesDetailsFragmentArgs by navArgs()
    private val sightsViewModel: SightsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_favourites_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavouritesDetailsBinding.bind(view)

        sightsViewModel.dataState.observe(viewLifecycleOwner, ::getDetails)
        sightsViewModel.loadSights(args.placeId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDetails(details: Sights) {
        _binding?.apply {
            with(details) {
                placeTitle.text = name
                if (image?.isNotEmpty() == true) {
                    Picasso.get()
                        .load(image)
                        .into(placeImage)
                }
                placeDescription.text = description
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) requireActivity().onBackPressed()
        return super.onOptionsItemSelected(menuItem)
    }

}