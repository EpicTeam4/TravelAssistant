package com.example.travelassistant.core.network.model

data class HotelResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<HotelModel> = listOf()
)
