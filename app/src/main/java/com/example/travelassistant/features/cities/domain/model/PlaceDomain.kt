package com.example.travelassistant.features.cities.domain.model

data class PlaceDomain(
    val id: String = "",
    val title: String = "",
    val shortTitle: String = "",
    val slug: String = "",
    val address: String = "",
    val location: String = "",
    val timeTable: String = "",
    val phone: String = "",
    val isStub: Boolean = true,
    val images: List<Image> = emptyList(),
    val shortDescription: String = "",
    val description: String = "",
    val siteUrl: String = "",
    val foreignUrl: String = "",
    val coordinates: Coords = Coords(0.0f, 0.0f),
    val subway: String = "",
    val favoritesCount: Int = 0,
    val commentsCount: Int = 0,
    val isClosed: Boolean = true,
    val categories: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val isUserFavorite: Boolean = false
)

data class Image(val url: String = "")
data class Coords(val latitude: Float = 0.0f, val longitude: Float = 0.0f)