package com.example.travelassistant.core.commands

import android.content.Intent

interface ViewCommand
data class GoToFragment(val pathId: Int): ViewCommand
data class SetAlarm(val intent: Intent, val time: Long): ViewCommand