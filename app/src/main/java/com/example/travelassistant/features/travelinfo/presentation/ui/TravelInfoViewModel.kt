package com.example.travelassistant.features.travelinfo.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.R
import com.example.travelassistant.core.Constants.EMPTY_STRING
import com.example.travelassistant.core.Constants.NOTIF_ID
import com.example.travelassistant.core.Constants.NOTIF_SECOND_ID
import com.example.travelassistant.core.domain.State
import com.example.travelassistant.core.domain.data
import com.example.travelassistant.core.domain.entity.City
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.parseError
import com.example.travelassistant.features.travelinfo.domain.usecase.GetInfoUseCase
import com.example.travelassistant.features.travelinfo.presentation.model.DateTime
import com.example.travelassistant.core.commands.CommandsLiveData
import com.example.travelassistant.core.commands.GoToFragment
import com.example.travelassistant.core.commands.SetAlarm
import com.example.travelassistant.core.commands.ViewCommand
import com.example.travelassistant.core.utils.DateTimeFormatter
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

    fun loadData() {
        viewModelScope.launch {
            dataContent.value = TravelInfoViewState.Loading
            val (cities, ports) = coroutineScope {
                val citiesResult = async { useCase.getCities() }
                val portsResult = async { useCase.getPorts() }

                citiesResult.await().data to portsResult.await().data
            }
            if (cities != null && ports != null) {
                handleData(cities = cities, ports = ports,)
            } else {
                handleError(true)
            }
        }
    }

    private suspend fun handleData(cities: List<City>, ports: List<Port>) {
        withContext(Main) {
            dataContent.value = content.copy(cities = cities, ports = ports)
        }
    }

    fun loadHotels(id: Long) {
        viewModelScope.launch {
            dataContent.value = TravelInfoViewState.Loading
            val citySlug = useCase.getCityById(id)
            coroutineScope {
                when (val hotels = citySlug?.slug.let { useCase.getHotels(it.toString()) }) {
                    is State.Success -> handleHotelsData(hotels = hotels.data)
                    is State.Error -> handleError(hotels.isNetworkError)
                }
            }
        }
    }

    private suspend fun handleError(isNetworkError: Boolean) {
        withContext(Main) {
            dataContent.value = TravelInfoViewState.Error(isNetworkError.parseError())
        }
    }

    private suspend fun handleHotelsData(hotels: List<Hotel>) {
        withContext(Main) {
            dataContent.value = content.copy(hotels = hotels)
        }
    }

    /* Date & Time */

    fun getDateInMillis() {
        tempDate = with(selectedDateTime) {
            formatter.getSelectedDateTimeInMillis(year, month, day, hours, minutes)
        }
        infoAboutTravel = infoAboutTravel.copyInfoAboutTravel(timeInMillis = tempDate)

        setDateTime()
    }

    fun getDateDestInMillis() {
        tempDate = with(selectedDateTime) {
            formatter.getSelectedDateTimeInMillis(year, month, day, hours, minutes)
        }
        infoAboutTravel = infoAboutTravel.copyInfoAboutTravel(timeInMillisDest = tempDate)

        setDateTimeDest()
    }

    private fun setDateTime() {
        viewModelScope.launch {
            val datetime = formatter.convertLongDateToString(tempDate)
            if (datetime != EMPTY_STRING) {
                dataContent.value = content.copy(datetime = datetime)
            }
        }
    }

    private fun setDateTimeDest() {
        viewModelScope.launch {
            val datetimeDest = formatter.convertLongDateToString(tempDate)
            if (datetimeDest != EMPTY_STRING) {
                dataContent.value = content.copy(dateTimeDest = datetimeDest)
            }
        }
    }

    /* Alarms */

    fun setAlarm() {
        if (infoAboutTravel.hours > 0 && infoAboutTravel.timeInMillis > 0) {
            val time = infoAboutTravel.timeInMillis - infoAboutTravel.hours - System.currentTimeMillis()
            val id = NOTIF_ID
            commands.onNext(SetAlarm(id, time))
        }
    }

    fun setSecondAlarm() {
        if (infoAboutTravel.hoursFromDest > 0 && infoAboutTravel.timeInMillisDest > 0) {
            val time = infoAboutTravel.timeInMillisDest - infoAboutTravel.hoursFromDest - System.currentTimeMillis()
            val id = NOTIF_SECOND_ID
            commands.onNext(SetAlarm(id, time))
        }
    }

    /* Navigation */

    fun openToDestination() {
        commands.onNext(GoToFragment(R.id.action_navigation_home_to_toDestinationFragment))
    }

    fun openFromDestination() {
        commands.onNext(GoToFragment(R.id.action_toDestinationFragment_to_fromDestinationFragment))
    }

    fun openHotelFragment() {
        commands.onNext(GoToFragment(R.id.action_fromDestinationFragment_to_hotelFragment))
    }

    fun openHomeFragment() {
        commands.onNext(GoToFragment(R.id.action_hotelFragment_to_navigation_home))
    }
}