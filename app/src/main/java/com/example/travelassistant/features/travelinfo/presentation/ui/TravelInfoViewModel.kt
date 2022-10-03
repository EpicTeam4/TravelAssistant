package com.example.travelassistant.features.travelinfo.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.features.travelinfo.domain.State
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

    fun loadData() {
        viewModelScope.launch {
            val (cities, ports) = coroutineScope {
                val citiesResult = async { useCase.getCities() }
                val portsResult = async { useCase.getPorts() }

                citiesResult.await().data to portsResult.await().data
            }

            if (cities != null && ports != null) {
                handleData(cities = cities, ports = ports, hotels = listOf())
            } else {
                handleError(true)
            }
        }
    }

    fun loadHotels(id: Long) {
        viewModelScope.launch {
            val citySlug = useCase.getCityById(id)
            if (citySlug?.slug != null) {
                when (val result = useCase.getHotels(citySlug.slug)) {
                    is State.Success -> handleData(
                        cities = listOf(),
                        ports = listOf(),
                        hotels = result.data
                    )
                    is State.Error -> handleError(result.isNetworkError)
                }
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
        hotels: List<Hotel>
    ) {
        withContext(Main) {
            dataContent.value =
                TravelInfoViewState.Content(
                    cities = cities,
                    ports = ports,
                    hotels = hotels
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