package com.example.travelassistant.features.travelinfo.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.EMPTY_STRING
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.domain.entity.PersonalItem
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.features.travelinfo.domain.State
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCase
import com.example.travelassistant.features.travelinfo.presentation.model.DateTime
import com.example.travelassistant.features.travelinfo.presentation.ui.commands.CommandsLiveData
import com.example.travelassistant.features.travelinfo.presentation.ui.commands.GoToFragment
import com.example.travelassistant.features.travelinfo.presentation.ui.commands.ViewCommand
import com.example.travelassistant.features.travelinfo.presentation.utils.DateTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
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
    var luggageItem = PersonalItem()
    var selectedHotelPos = 0
    var tempDate: Long = 0
    var content = TravelInfoViewState.Content()

    val commands = CommandsLiveData<ViewCommand>()

    private val dataContent = MutableLiveData<TravelInfoViewState>()
    val dataState: LiveData<TravelInfoViewState> get() = dataContent

    fun addDetails(info: InfoAboutTravel) {
        viewModelScope.launch {
            withContext(Main) {
                useCase.addDetails(info)
            }
        }
    }

    fun addItem(item: PersonalItem) {
        viewModelScope.launch {
            withContext(Main) {
                useCase.addItem(item)
            }
        }
    }

    fun loadCities() {
        viewModelScope.launch {
            coroutineScope {
                when (val cities = useCase.getCities()) {
                    is State.Success -> handleCitiesData(cities = cities.data)
                    is State.Error -> handleError(cities.isNetworkError)
                }
            }
        }
    }

    fun loadPorts() {
        viewModelScope.launch {
            coroutineScope {
                when (val ports = useCase.getPorts()) {
                    is State.Success -> handlePortsData(ports = ports.data)
                    is State.Error -> handleError(ports.isNetworkError)
                }
            }
        }
    }

    fun loadHotels(id: Long) {
        viewModelScope.launch {
            val citySlug = useCase.getCityById(id)
            coroutineScope {
                when (val hotels = citySlug?.slug?.let { useCase.getHotels(it) }) {
                    is State.Success -> handleHotelsData(hotels = hotels.data)
                    is State.Error -> handleError(hotels.isNetworkError)
                    null -> handleError(true)
                }
            }
        }
    }

    fun loadItems() {
        viewModelScope.launch {
            coroutineScope {
                when (val items = useCase.getAllItems()) {
                    is State.Success -> handleItemsData(items = items.data)
                    is State.Error -> handleError(items.isNetworkError)
                }
            }
        }
    }

    fun setDateTime() {
        viewModelScope.launch {
            val datetime = formatter.convertLongDateToString(tempDate)
            if (datetime != EMPTY_STRING) {
                handleDateTime(datetime = datetime)
            }
        }
    }

    private suspend fun handleError(isNetworkError: Boolean) {
        withContext(Main) {
            dataContent.value = TravelInfoViewState.Error(isNetworkError.parseError())
        }
    }

    private suspend fun handleCitiesData(cities: List<City>) {
        withContext(Main) {
            dataContent.value = content.copy(cities = cities)
        }
    }

    private suspend fun handlePortsData(ports: List<Port>) {
        withContext(Main) {
            dataContent.value = content.copy(ports = ports)
        }
    }

    private suspend fun handleHotelsData(hotels: List<Hotel>) {
        withContext(Main) {
            dataContent.value = content.copy(hotels = hotels)
        }
    }

    private suspend fun handleItemsData(items: List<PersonalItem>) {
        withContext(Main) {
            dataContent.value = content.copy(items = items)
        }
    }

    private suspend fun handleDateTime(datetime: String) {
        withContext(Main) {
            dataContent.value = content.copy(datetime = datetime)
        }
    }

    fun openToDestination() {
        commands.onNext(GoToFragment(R.id.action_navigation_home_to_toDestinationFragment))
    }

    fun openFromDestination() {
        commands.onNext(GoToFragment(R.id.action_toDestinationFragment_to_fromDestinationFragment))
    }

    fun openHotelFragment() {
        commands.onNext(GoToFragment(R.id.action_fromDestinationFragment_to_hotelFragment))
    }

    fun openItemsFragment() {
        commands.onNext(GoToFragment(R.id.action_hotelFragment_to_personalItemsFragment))
    }

    fun openHomeFragment() {
        commands.onNext(GoToFragment(R.id.action_personalItemsFragment_to_navigation_home))
    }
}