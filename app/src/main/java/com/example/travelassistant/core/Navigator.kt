package com.example.travelassistant.core

import android.app.Activity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.travelassistant.R

/**
 * Navigator - реализует переход между фрагментами
 *
 * @author Marianne Sabanchieva
 */

object Navigator {
    fun navigateToDestinationFragment(activity: Activity, args: Bundle) {
        activity.findNavController(R.id.navHostFragment)
            .navigate(R.id.action_toDestinationFragment_to_fromDestinationFragment, args)
    }

    fun navigateToHotelFragment(activity: Activity, args: Bundle) {
        activity.findNavController(R.id.navHostFragment)
            .navigate(R.id.action_fromDestinationFragment_to_hotelFragment, args)
    }
}