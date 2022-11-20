package com.example.travelassistant.features.cities.data

import com.example.travelassistant.core.database.dao.SightsDao
import com.example.travelassistant.core.domain.entity.Sights
import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.core.network.model.Place
import com.example.travelassistant.features.cities.domain.model.Coords
import com.example.travelassistant.features.cities.domain.model.Image
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.example.travelassistant.features.cities.domain.repository.PlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesRepositoryImpl(
    private val kudagoClient: KudagoClient,
    private val sightsDao: SightsDao
) : PlacesRepository {

    private val PLACE_FIELDS_FOR_PLACES_SCREEN =
        "id,title,images,description,slug,location"

    private val PLACE_ALL_FIELDS =
        "id,title,short_title,slug,address,location,timetable,phone,is_stub,images," +
                "description,body_text,site_url,foreign_url,coords,subway,favorites_count," +
                "comments_count,is_closed,categories,tags"

    override suspend fun getPlaces(location: String): List<PlaceDomain> {
        return withContext(Dispatchers.IO) {
            val sights = sightsDao.getFavouriteSights()
            kudagoClient.getPlacesWithFieldsOrderByFavoritesCountDesc(
                location, PLACE_FIELDS_FOR_PLACES_SCREEN
            ).let { response ->
                return@let response.results
                    ?.filter { place -> excludeOnlineLocation(place)}
                    ?.map { place ->
                        mapNetworkPlaceToDomainPlace(
                            place,
                            isUserFavorite(sights, place.slug.orEmpty())
                        )
                    } ?: emptyList()
            }
        }
    }

    override suspend fun getPlace(placeId: String): PlaceDomain {
        return withContext(Dispatchers.IO) {
            val sights = sightsDao.getFavouriteSights()
            kudagoClient.getPlaceWithFields(
                placeId, PLACE_ALL_FIELDS
            ).let { response ->
                mapNetworkPlaceToDomainPlace(response, isUserFavorite(sights, response.slug.orEmpty()))
            }
        }
    }

    override suspend fun addPlaceToFavorites(place: PlaceDomain) {
        withContext(Dispatchers.IO) {
            sightsDao.addSightToFavourite(place.title, place.slug)
        }
    }

   override suspend fun deletePlaceFromFavorites(place: PlaceDomain) {
        withContext(Dispatchers.IO) {
            sightsDao.deleteSightFromFavourite(place.slug)
        }
    }

}

    private fun excludeOnlineLocation(place: Place): Boolean {
        return place.location != "online"
    }

fun isUserFavorite(sights: List<Sights>, slug: String): Boolean {
    return sights.firstOrNull { sight -> sight.slug.equals(slug) } != null
}

fun mapNetworkPlaceToDomainPlace(source: Place, isUserFavorite: Boolean): PlaceDomain {
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
        shortDescription = source.description.orEmpty().deleteHtmlTags(),
        description = source.body_text.orEmpty().deleteHtmlTags(),
        siteUrl = source.site_url.orEmpty(),
        foreignUrl = source.foreign_url.orEmpty(),
        coordinates = Coords(source.coords?.lat ?: 0.0f, source.coords?.lon ?: 0.0f), // todo можно вынести в extension типа Float?.orZero() = if (this == null) 0.0f else this
        subway = source.subway.orEmpty(),
        favoritesCount = source.favorites_count ?: 0,
        commentsCount = source.comments_count ?: 0,
        isClosed = source.is_closed ?: false,
        categories = source.categories?.map { i -> i.orEmpty() } ?: emptyList(),
        tags = source.tags?.map { i -> i.orEmpty() } ?: emptyList(),
        isUserFavorite = isUserFavorite
    )
}

fun String.deleteHtmlTags(): String {
    return this.replace("<[^>]*>".toRegex(), "")
}
