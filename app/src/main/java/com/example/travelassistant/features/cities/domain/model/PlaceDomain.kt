package com.example.travelassistant.features.cities.domain.model

data class PlaceDomain(
    val id: String,
    val title: String,
    val shortTitle: String,
    val slug: String,
    val address: String,
    val location: String,
    val timeTable: String,
    val phone: String,
    val isStub: Boolean,
    val images: List<Image>,
    val shortDescription: String,
    val description: String,
    val siteUrl: String,
    val foreignUrl: String,
    val coordinates: Coords,
    val subway: String,
    val favoritesCount: Int,
    val commentsCount: Int,
    val isClosed: Boolean,
    val categories: List<String>,
    val tags: List<String>
)

data class Image(val url: String)
data class Coords(val latitude: Float, val longitude: Float)