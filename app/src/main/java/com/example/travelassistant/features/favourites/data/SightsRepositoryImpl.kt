package com.example.travelassistant.features.favourites.data

import com.example.travelassistant.core.domain.LocalDataSource
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.network.KudagoClient
import com.example.travelassistant.core.network.model.Place
import com.example.travelassistant.features.cities.data.isUserFavorite
import com.example.travelassistant.features.cities.data.mapNetworkPlaceToDomainPlace
import com.example.travelassistant.features.cities.domain.model.PlaceDomain
import com.example.travelassistant.features.favourites.domain.repository.SightsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Реализация методов репозитория
 *
 * @author Marianne Sabanchieva
 */

class SightsRepositoryImpl(
    private val kudagoClient: KudagoClient,
    private val dataSource: LocalDataSource
) : SightsRepository {

    private val PLACE_FIELDS_FOR_PLACES_SCREEN =
        "id,title,images,description,slug,location"

    private val PLACE_ALL_FIELDS =
        "id,title,short_title,slug,address,location,timetable,phone,is_stub,images," +
                "description,body_text,site_url,foreign_url,coords,subway,favorites_count," +
                "comments_count,is_closed,categories,tags"

    override suspend fun getFavouriteSights(location: String): List<PlaceDomain> {
        return withContext(Dispatchers.IO) {
            val sights = dataSource.getFavouriteSights()
            kudagoClient.getPlacesWithFieldsOrderByFavoritesCountDesc(
                location, PLACE_FIELDS_FOR_PLACES_SCREEN
            ).let { response ->
                return@let response.results
                    ?.filter { place ->
                        excludeOnlineLocation(place)
                        isUserFavorite(sights, place.slug.orEmpty())
                    }
                    ?.map { place ->
                        mapNetworkPlaceToDomainPlace(
                            place,
                            isUserFavorite(sights, place.slug.orEmpty())
                        )
                    } ?: emptyList()
            }
        }
    }

    override suspend fun getSightsById(placeId: String): PlaceDomain {
        return withContext(Dispatchers.IO) {
            val sights = dataSource.getFavouriteSights()
            kudagoClient.getPlaceWithFields(
                placeId, PLACE_ALL_FIELDS
            ).let { response ->
                mapNetworkPlaceToDomainPlace(response, isUserFavorite(sights, response.slug.orEmpty()))
            }
        }
    }

    private fun excludeOnlineLocation(place: Place): Boolean {
        return place.location != "online"
    }

    override suspend fun getCities(): List<City> = dataSource.getCities()

    override suspend fun deleteSightFromFavourites(place: PlaceDomain) {
        withContext(Dispatchers.IO) {
            dataSource.deleteSightFromFavourite(place.slug)
        }
    }
}