package com.example.travelassistant.core.network

import com.example.travelassistant.core.network.model.Location
import com.example.travelassistant.core.network.model.PlacesResponse
import com.example.travelassistant.core.network.model.HotelResponse
import com.example.travelassistant.core.network.model.Place
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KudagoClientApi {

    @GET("locations")
    suspend fun getLocations(): List<Location>

    @GET("places")
    suspend fun getPlaces(
        @Query(
            value = "location",
            encoded = true
        ) location: String
    ): PlacesResponse

    @GET("places")
    suspend fun getPlacesWithFields( // todo fields сделать enum
        @Query(value = "location", encoded = true) location: String,
        @Query(value = "fields", encoded = true) fields: String
    ): PlacesResponse

    @GET("places")
    suspend fun getPlacesWithLocationFieldsOrderBy(
        @Query(value = "location", encoded = true) location: String,
        @Query(value = "fields", encoded = true) fields: String,
        @Query(value = "order_by", encoded = true) orderBy: String
    ): PlacesResponse

    @GET("places/{placeId}")
    suspend fun getPlaceByIdWithFields(
        @Path(value = "placeId", encoded = true) placeId: String,
        @Query(value = "fields", encoded = true) fields: String,
    ): Place

    @GET("places/?categories=inn")
    suspend fun getHotels(@Query("location") location: String): HotelResponse
}