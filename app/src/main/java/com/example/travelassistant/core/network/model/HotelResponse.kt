package com.example.travelassistant.core.network.model

import com.example.travelassistant.core.Constants.EMPTY_STRING

data class HotelResponse(
    val count: Int? = 0,
    val next: String? = EMPTY_STRING,
    val previous: String? = EMPTY_STRING,
    val results: List<HotelModel> = listOf()
)
