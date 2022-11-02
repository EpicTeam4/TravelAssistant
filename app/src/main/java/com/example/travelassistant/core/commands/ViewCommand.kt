package com.example.travelassistant.core.commands

import android.content.Intent
import androidx.navigation.NavDirections

interface ViewCommand
data class GoToFragment(val pathId: Int) : ViewCommand
data class SendArgsToFragment(val nav: NavDirections) : ViewCommand
data class SetAlarm(val intent: Intent, val time: Long) : ViewCommand