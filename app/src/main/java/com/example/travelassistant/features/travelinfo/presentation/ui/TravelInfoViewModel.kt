package com.example.travelassistant.features.travelinfo.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.PersonalItem
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.features.travelinfo.domain.data
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCase
import com.example.travelassistant.features.travelinfo.presentation.model.DateTime
import com.example.travelassistant.features.travelinfo.presentation.model.InfoAboutTravel
import com.example.travelassistant.features.travelinfo.presentation.ui.commands.CommandsLiveData
import com.example.travelassistant.features.travelinfo.presentation.ui.commands.GoToFragment
import com.example.travelassistant.features.travelinfo.presentation.ui.commands.ViewCommand
import com.example.travelassistant.features.travelinfo.presentation.utils.DateTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * View model для хранения списка городов
 *
 * @author Marianne Sabanchieva
 */

@HiltViewModel
class TravelInfoViewModel @Inject constructor(
    private val useCase: GetInfoUseCase,
    val formatter: DateTimeFormatter
) : ViewModel() {

    var selectedDateTime = DateTime()
    var infoAboutTravel = InfoAboutTravel()
    var selectedHotelPos = 0
    var tempDate: Long = 0

    val commands = CommandsLiveData<ViewCommand>()

    private val dataContent = MutableLiveData<TravelInfoViewState>()
    val dataState: LiveData<TravelInfoViewState> get() = dataContent

    fun loadData(id: Long) {
        viewModelScope.launch {
            val (cities, ports) = coroutineScope {
                val citiesResult = async { useCase.getCities() }
                val portsResult = async { useCase.getPorts() }

                citiesResult.await().data to portsResult.await().data
            }

            val citySlug = useCase.getCityById(id)
            val (hotels, items) = coroutineScope {
                val hotelsResult = async { citySlug?.slug?.let { useCase.getHotels(it) } }
                val itemsResult = async { useCase.getAllItems() }

                hotelsResult.await()?.data to itemsResult.await().data
            }

            if (cities != null && ports != null && hotels != null && items != null) {
                handleData(cities = cities, ports = ports, hotels = hotels, items = items)
            } else if (cities != null && ports != null && hotels.isNullOrEmpty() && items != null) {
                handleData(cities = cities, ports = ports, hotels = listOf(), items = items)
            } else {
                handleError(true)
            }
        }
    }

    private suspend fun handleError(isNetworkError: Boolean) {
        withContext(Main) {
            dataContent.value = TravelInfoViewState.Error(isNetworkError.parseError())
        }
    }

    private suspend fun handleData(
        cities: List<City>,
        ports: List<Port>,
        hotels: List<Hotel>,
        items: List<PersonalItem>
    ) {
        withContext(Main) {
            dataContent.value =
                TravelInfoViewState.Content(
                    cities = cities, ports = ports, hotels = hotels, items = items
                )
        }
    }

    fun openToDestination(pathId: NavDirections) {
        commands.onNext(GoToFragment(pathId))
    }

    fun openFromDestination(pathId: NavDirections) {
        commands.onNext(GoToFragment(pathId))
    }

    fun openHotelFragment(pathId: NavDirections) {
        commands.onNext(GoToFragment(pathId))
    }

    fun openItemsFragment(pathId: NavDirections) {
        commands.onNext(GoToFragment(pathId))
    }
}