package com.example.travelassistant.core.network

import com.example.travelassistant.core.network.model.Location
import com.example.travelassistant.core.network.model.PlacesResponse
import com.example.travelassistant.core.network.model.HotelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KudagoClientApi {

    @GET("locations")
    suspend fun getLocations(): List<Location>

    @GET("places/?location")
    suspend fun getPlaces(
        @Query(
            value = "location",
            encoded = true
        ) location: String
    ): PlacesResponse

    @GET("places/?location")
    suspend fun getPlacesWithFields( // todo fields сделать enum
        @Query(value = "location", encoded = true) location: String,
        @Query(value = "fields", encoded = true) fields: String
    ): PlacesResponse

    @GET("places/?categories=inn")
    suspend fun getHotels(@Query("location") location: String): HotelResponse
}