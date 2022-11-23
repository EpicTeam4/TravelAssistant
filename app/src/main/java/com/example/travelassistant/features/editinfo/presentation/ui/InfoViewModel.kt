package com.example.travelassistant.features.editinfo.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelassistant.core.Constants.AIRPORT
import com.example.travelassistant.core.Constants.EMPTY_STRING
import com.example.travelassistant.core.Constants.NOTIF_ID
import com.example.travelassistant.core.Constants.NOTIF_SECOND_ID
import com.example.travelassistant.core.Constants.RAILWAY
import com.example.travelassistant.core.domain.data
import com.example.travelassistant.core.domain.entity.Hotel
import com.example.travelassistant.core.domain.entity.InfoAboutTravel
import com.example.travelassistant.core.domain.entity.Port
import com.example.travelassistant.core.parseError
import com.example.travelassistant.features.editinfo.domain.usecase.GetEditInfoUseCase
import com.example.travelassistant.features.editinfo.presentation.model.InfoEditingDateTime
import com.example.travelassistant.core.commands.CommandsLiveData
import com.example.travelassistant.core.commands.SetAlarm
import com.example.travelassistant.core.commands.ViewCommand
import com.example.travelassistant.core.utils.DateTimeFormatter
import com.example.travelassistant.core.utils.toPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * View model для хранения информации о поездке
 * @param infoAboutTravel - копия события, подгружаемого из БД, для обновления данных
 * с последующим их внесением в таблицу БД
 *
 * @author Marianne Sabanchieva
 */

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val useCase: GetEditInfoUseCase,
    val formatter: DateTimeFormatter
) : ViewModel() {

    lateinit var infoAboutTravel: InfoAboutTravel
    var content = InfoViewState.Content()
    var selectedDateTime = InfoEditingDateTime()
    var tempDate: Long = 0
    var selectedHotelPos = -1
    var selectedHotelId = 0

    val commands = CommandsLiveData<ViewCommand>()

    private val dataContent = MutableLiveData<InfoViewState>()
    val dataState: LiveData<InfoViewState> get() = dataContent

    fun loadDetails(date: Long) {
        viewModelScope.launch {
            useCase.getHometown().collect() {
                loadInfoDetails(it, date)
            }
        }
    }

    private fun loadInfoDetails(cityId: Int, date: Long) {
        viewModelScope.launch {
            dataContent.value = InfoViewState.Loading
            val (event, ports, hotels) = coroutineScope {
                val eventResult = async { useCase.getDetails(date) }
                val portsResult = async { useCase.getPorts() }
                val citySlug = eventResult.await().data?.let { useCase.getCityById(it.city_id) }
                val hotelsResult = async { citySlug?.slug?.let { useCase.getHotels(it) } }

                awaitAll(eventResult, portsResult, hotelsResult)
            }

            if (event != null && ports != null && hotels != null) {
                infoAboutTravel = event.data as InfoAboutTravel
                handleDetailsData(
                    event = event.data as InfoAboutTravel,
                    airports = if (cityId != DEFAULT_VALUE) {
                        (ports.data as List<Port>).filter { port -> port.location == cityId.toLong() && port.slug == "airport" }
                    } else {
                        (ports.data as List<Port>).filter { port -> port.slug == "airport" }
                    },
                    railways = if (cityId != DEFAULT_VALUE) {
                        (ports.data as List<Port>).filter { port -> port.location == cityId.toLong() && port.slug == "railway" }
                    } else {
                        (ports.data as List<Port>).filter { port -> port.slug == "railway" }
                    },
                    airportsDest = (ports.data as List<Port>).filter {
                        port -> port.location == infoAboutTravel.city_id && port.slug == "airport"
                    },
                    railwaysDest = (ports.data as List<Port>).filter {
                        port -> port.location == infoAboutTravel.city_id && port.slug == "railway"
                    },
                    hotels = emptyList()
                )
            } else {
                handleError(false)
            }
        }
    }

    private suspend fun handleError(isNetworkError: Boolean) {
        withContext(Dispatchers.Main) {
            dataContent.value = InfoViewState.Error(isNetworkError.parseError())
        }
    }

    private suspend fun handleDetailsData(
        event: InfoAboutTravel?,
        airports: List<Port>,
        railways: List<Port>,
        airportsDest: List<Port>,
        railwaysDest: List<Port>,
        hotels: List<Hotel>
    ) {
        withContext(Dispatchers.Main) {
            dataContent.value =
                content.copy(
                    event = event,
                    airports = airports,
                    railways = railways,
                    airportsDest = airportsDest,
                    railwaysDest = railwaysDest,
                    hotels = hotels
                )
        }
    }

    /* Update info in DB */

    fun updateDetails(info: InfoAboutTravel) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                useCase.updateDetails(info)
            }
        }
    }

    /* Alarms */

    fun setAlarm() {
        if (infoAboutTravel.hours > 0 && infoAboutTravel.timeInMillis > 0) {
            val time =
                infoAboutTravel.timeInMillis - infoAboutTravel.hours - System.currentTimeMillis()
            val id = NOTIF_ID
            commands.onNext(SetAlarm(id, time, infoAboutTravel.wayDescription))
        }
    }

    fun setSecondAlarm() {
        if (infoAboutTravel.hoursFromDest > 0 && infoAboutTravel.timeInMillisDest > 0) {
            val time =
                infoAboutTravel.timeInMillisDest - infoAboutTravel.hoursFromDest - System.currentTimeMillis()
            val id = NOTIF_SECOND_ID
            commands.onNext(SetAlarm(id, time, infoAboutTravel.wayDescriptionFromDest))
        }
    }

    /* Date & Time */

    fun getDateInMillis() {
        tempDate = with(selectedDateTime) {
            formatter.getSelectedDateTimeInMillis(year, month, day, hours, minutes)
        }
        infoAboutTravel = infoAboutTravel.copyInfoAboutTravel(timeInMillis = tempDate)

        setAlarm()
    }

    fun getDateDestInMillis() {
        tempDate = with(selectedDateTime) {
            formatter.getSelectedDateTimeInMillis(year, month, day, hours, minutes)
        }
        infoAboutTravel = infoAboutTravel.copyInfoAboutTravel(timeInMillisDest = tempDate)

        setSecondAlarm()
    }

    fun setPortType(avia: Boolean) {
        infoAboutTravel = if (avia) {
            infoAboutTravel.copyInfoAboutTravel(portType = AIRPORT)
        } else {
            infoAboutTravel.copyInfoAboutTravel(portType = RAILWAY)
        }
    }

    fun setPortDestType(avia: Boolean) {
        infoAboutTravel = if (avia) {
            infoAboutTravel.copyInfoAboutTravel(destPortType = AIRPORT)
        } else {
            infoAboutTravel.copyInfoAboutTravel(destPortType = RAILWAY)
        }
    }

    /* Formats */

    fun convertMillisToText(time: Long?): String {
        return if (time != null) {
            formatter.convertLongDateToString(time)
        } else {
            EMPTY_STRING
        }
    }

    fun convertHoursToAdapterPosition(hours: Long?): Int {
        return if (hours != null) {
            formatter.convertMillisecondsToHours(hours).toPosition()
        } else {
            default_hours_position
        }
    }

    companion object {
        const val default_hours_position = 0
        const val DEFAULT_VALUE = 0
    }
}