package com.example.travelassistant.features.travelinfo.presentation.ui.commands

import androidx.navigation.NavDirections

interface ViewCommand
data class GoToFragment(val pathId: NavDirections): ViewCommand