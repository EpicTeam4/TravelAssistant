package com.example.travelassistant.features.hometown.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.travelassistant.R
import com.example.travelassistant.databinding.FragmentHometownBinding

class HometownDialogFragment : DialogFragment() {

    private var _binding: FragmentHometownBinding? = null
    private lateinit var citiesList: ArrayAdapter<String>
    private val viewModel: HometownViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hometown, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHometownBinding.bind(view)

        initObservers()
        viewModel.loadData()

        _binding?.apply {
            save.setOnClickListener {
                viewModel.savePreferences(spinner.selectedItemPosition + 1)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        citiesList =
            (ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        viewModel.dataState.observe(viewLifecycleOwner, ::handleState)
        _binding?.spinner?.adapter = citiesList
    }

    private fun handleState(state: HometownViewState) {
        refresh(state)
        when (state) {
            is HometownViewState.Loading -> refresh(state)
            is HometownViewState.Content -> state.handle()
            is HometownViewState.Error -> state.handle()
        }
    }

    private fun HometownViewState.Content.handle() {
        if (citiesList.isEmpty) cities.forEach { citiesList.add(it.name) }
        if (cityId != DEFAULT_VALUE) {
            _binding?.spinner?.setSelection(cityId - 1)
        } else {
            _binding?.spinner?.setSelection(DEFAULT_VALUE)
        }
    }

    private fun HometownViewState.Error.handle() {
        _binding?.errorPanel?.apply {
            errorIcon.setImageResource(errorModel.icon)
            errorTitle.setText(errorModel.title)
        }
    }

    private fun refresh(state: HometownViewState) {
        _binding?.apply {
            progressBar.isVisible = state is HometownViewState.Loading
            contentPanel.isVisible = state is HometownViewState.Content
            errorPanel.root.isVisible = state is HometownViewState.Error
        }
    }

    companion object {
        const val DEFAULT_VALUE = 0
        const val TAG = "myDialog"
        fun newInstance(): HometownDialogFragment {
            return HometownDialogFragment()
        }
    }
}