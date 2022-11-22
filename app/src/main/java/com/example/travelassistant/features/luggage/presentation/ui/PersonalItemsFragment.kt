package com.example.travelassistant.features.luggage.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.EMPTY_STRING
import com.example.travelassistant.databinding.FragmentPersonalItemsBinding
import com.example.travelassistant.features.luggage.presentation.adapters.ItemAdapter

class PersonalItemsFragment : Fragment() {

    private var _binding: FragmentPersonalItemsBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemsAdapter: ItemAdapter
    private val luggageViewModel: LuggageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPersonalItemsBinding.bind(view)

        itemsAdapter = ItemAdapter(mutableListOf()) { id -> deleteItem(id) }
        recyclerView = (view.findViewById(R.id.items) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemsAdapter
        }

        _binding?.apply {
            addItem.setOnClickListener {
                addNewItem()
            }
        }

        with(luggageViewModel) {
            loadData()
            dataState.observe(viewLifecycleOwner, ::handleState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(state: LuggageViewState) {
        refresh(state)
        when (state) {
            is LuggageViewState.Loading -> refresh(state)
            is LuggageViewState.Content -> state.handle()
            is LuggageViewState.Error -> state.handle()
        }
    }

    private fun LuggageViewState.Content.handle() {
        itemsAdapter.setItems(items)
    }

    private fun LuggageViewState.Error.handle() {
        _binding?.errorPanel?.apply {
            errorIcon.setImageResource(errorModel.icon)
            errorTitle.setText(errorModel.title)
        }
    }

    private fun refresh(state: LuggageViewState) {
        _binding?.apply {
            progressBar.isVisible  = state is LuggageViewState.Loading
            contentPanel.isVisible = state is LuggageViewState.Content
            errorPanel.root.isVisible = state is LuggageViewState.Error
        }
    }

    private fun deleteItem(id: Int) {
        luggageViewModel.deleteItem(id)
    }

    private fun addNewItem() {
        with(luggageViewModel) {
            luggageItem = luggageItem.copyItem(item = _binding?.newItem?.text.toString())
            addItem(luggageItem)
            itemsAdapter.addItem(luggageItem)
            _binding?.newItem?.setText(EMPTY_STRING)
        }
    }
}