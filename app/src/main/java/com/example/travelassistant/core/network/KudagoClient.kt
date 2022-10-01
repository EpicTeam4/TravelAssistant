package com.example.travelassistant.core.network

import com.example.travelassistant.core.network.model.Location
import com.example.travelassistant.core.network.model.PlacesResponse
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.exitProcess

class KudagoClient {

    private val BASE_URL = "https://kudago.com/public-api/v1.4/" // todo вынести

    private val retrofit: KudagoClientApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient()
                .newBuilder()
                .addInterceptor(RequestResponseInterceptor)
                .build()
        )
        .baseUrl(BASE_URL)
        .build()
        .create(KudagoClientApi::class.java)

    object RequestResponseInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            println("Request $request")
            val response = chain.proceed(request)
            println("Response $response")
            return response
        }
    }

    suspend fun getLocations(): List<Location> {
        return retrofit.getLocations();
    }

    suspend fun getPlaces(location: String): PlacesResponse {
        return retrofit.getPlaces(location);
    }

    suspend fun getPlacesWithFields(location: String, fields: String): PlacesResponse {
        return retrofit.getPlacesWithFields(location, fields);
    }

    suspend fun getPlacesWithFieldsOrderByFavoritesCountDesc(location: String, fields: String): PlacesResponse {
        return retrofit.getPlacesWithLocationFieldsOrderBy(location, fields, "-favorites_count");
    }

}


fun main(): Unit = // todo move to junit test
    runBlocking {
        println(KudagoClient().getLocations())
        println(KudagoClient().getPlaces("spb"))
        println(KudagoClient().getPlacesWithFields("spb", "id"))
        println(
            KudagoClient().getPlacesWithFields(
                "spb",
                "id,title,short_title,slug,address,location,timetable,phone,is_stub,images," +
                        "description,body_text,site_url,foreign_url,coords,subway,favorites_count," +
                        "comments_count,is_closed,categories,tags"
            )
        )
        exitProcess(0)
    }

