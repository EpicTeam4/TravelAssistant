package com.example.travelassistant.core.commands

import androidx.navigation.NavDirections

interface ViewCommand
data class GoToFragment(val pathId: Int) : ViewCommand
data class GoToFragmentAndSendSafeArgs(val nav: NavDirections) : ViewCommand
data class SetAlarm(val id: Int, val time: Long) : ViewCommand