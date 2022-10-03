package com.example.travelassistant.core

import android.app.Activity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.travelassistant.R

/**
 * To navigate - функция, описывающая общий процесс перехода к фрагменту
 *
 * @author Marianne Sabanchieva
 */

fun Activity.toNavigate(pathId: NavDirections) =
    this.findNavController(R.id.navHostFragment).navigate(pathId)