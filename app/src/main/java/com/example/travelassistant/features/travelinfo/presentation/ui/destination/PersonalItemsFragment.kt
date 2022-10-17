package com.example.travelassistant.features.travelinfo.presentation.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.DEFAULT_VALUE
import com.example.travelassistant.databinding.FragmentPersonalItemsBinding
import com.example.travelassistant.features.travelinfo.presentation.adapters.ItemAdapter
import com.example.travelassistant.features.travelinfo.presentation.ui.TravelInfoViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalItemsFragment : BaseFragment() {

    private var _binding: FragmentPersonalItemsBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemsAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal_items, container, false)
        infoViewModel.loadData(DEFAULT_VALUE)
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPersonalItemsBinding.bind(view)

        itemsAdapter = ItemAdapter(mutableListOf())
        recyclerView = (view.findViewById(R.id.items) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemsAdapter
        }

        infoViewModel.dataState.observe(viewLifecycleOwner, ::handleState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(state: TravelInfoViewState) {
        refresh(state)
        when(state) {
            is TravelInfoViewState.Content -> state.handle()
            is TravelInfoViewState.Error -> state.handle()
        }
    }

    private fun TravelInfoViewState.Content.handle() {
        itemsAdapter.setItems(items)
    }

    private fun TravelInfoViewState.Error.handle() {
        with(_binding?.errorPanel) {
            this?.errorIcon?.setImageResource(errorModel.icon)
            this?.errorTitle?.setText(errorModel.title)
        }
    }

    private fun refresh(state: TravelInfoViewState) {
        with(_binding) {
            this?.contentPanel?.isVisible = state is TravelInfoViewState.Content
            this?.errorPanel?.root?.isVisible = state is TravelInfoViewState.Error
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}