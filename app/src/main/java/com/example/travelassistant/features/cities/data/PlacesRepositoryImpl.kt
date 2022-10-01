package com.example.travelassistant.features.cities.data

import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.core.network.model.Place
import com.example.travelassistant.features.cities.domain.model.Coords
import com.example.travelassistant.features.cities.domain.model.Image
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.example.travelassistant.features.cities.domain.repository.PlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesRepositoryImpl(private val kudagoClient: KudagoClient) : PlacesRepository {

    override suspend fun getPlaces(location: String): Result<List<PlaceDomain>> {
        return withContext(Dispatchers.IO) {
            safeCall {
                kudagoClient.getPlacesWithFieldsOrderByFavoritesCountDesc(
                    location,
                    "id,title,short_title,slug,address,location,timetable,phone,is_stub,images," +
                            "description,body_text,site_url,foreign_url,coords,subway,favorites_count," +
                            "comments_count,is_closed,categories,tags"
                ).let { response ->
                    return@let response.results?.map { place -> mapNetworkPlaceToDomainPlace(place)
                    } ?: listOf()
                }
            }
        }
    }

}

fun mapNetworkPlaceToDomainPlace(source: Place): PlaceDomain {
    return PlaceDomain(
        id = source.id.toString(),
        title = source.title.orEmpty(),
        shortTitle = source.short_title.orEmpty(),
        slug = source.slug.orEmpty(),
        address = source.address.orEmpty(),
        location = source.location.orEmpty(),
        timeTable = source.timetable.orEmpty(),
        phone = source.phone.orEmpty(),
        isStub = source.is_stub ?: false,
        images = source.images?.map { i -> Image(i?.image.orEmpty()) } ?: emptyList(),
        shortDescription = source.description.orEmpty(),
        description = source.body_text.orEmpty(),
        siteUrl = source.site_url.orEmpty(),
        foreignUrl = source.foreign_url.orEmpty(),
        coordinates = Coords(source.coords?.lat ?: 0.0f, source.coords?.lon ?: 0.0f),
        subway = source.subway.orEmpty(),
        favoritesCount = source.favorites_count ?: 0,
        commentsCount = source.comments_count ?: 0,
        isClosed = source.is_closed ?: false,
        categories = source.categories?.map { i -> i.orEmpty() } ?: emptyList(),
        tags = source.tags?.map { i -> i.orEmpty() } ?: emptyList(),
    )
}