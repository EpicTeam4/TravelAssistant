package com.example.travelassistant.features.travelinfo.presentation.model

import com.example.travelassistant.core.Constants.EMPTY_STRING

data class InfoAboutTravel(
    val timeInMillis: Long = 0,
    val portId: Int = 0,
    val flightNum: String = EMPTY_STRING,
    val seat: String = EMPTY_STRING,
    val wayDescription: String = EMPTY_STRING,
    val hours: Long = 0,
    val timeInMillisDest: Long = 0,
    val hoursFromDest: Long = 0,
    val destPortId: Int = 0,
    val flightNumFromDest: String = EMPTY_STRING,
    val seatFromDest: String = EMPTY_STRING,
    val wayDescriptionFromDest: String = EMPTY_STRING,
    val hotelId: Int = 0,
    val hotelAddress: String = EMPTY_STRING,
    val hotelPhone: String = EMPTY_STRING,
    val hotelStation: String = EMPTY_STRING,
    val wayToHotel: String = EMPTY_STRING
) {
    fun copyInfoAboutTravel(
        timeInMillis: Long = this.timeInMillis,
        portId: Int = this.portId,
        flightNum: String = this.flightNum,
        seat: String = this.seat,
        wayDescription: String = this.wayDescription,
        hours: Long = this.hours,
        timeInMillisDest: Long = this.timeInMillisDest,
        hoursFromDest: Long = this.hoursFromDest,
        destPortId: Int = this.destPortId,
        flightNumFromDest: String = this.flightNumFromDest,
        seatFromDest: String = this.seatFromDest,
        wayDescriptionFromDest: String = this.wayDescriptionFromDest,
        hotelId: Int = this.hotelId,
        hotelAddress: String = this.hotelAddress,
        hotelPhone: String = this.hotelPhone,
        hotelStation: String = this.hotelStation,
        wayToHotel: String = this.wayToHotel
    ) =
        InfoAboutTravel(
            timeInMillis,
            portId,
            flightNum,
            seat,
            wayDescription,
            hours,
            timeInMillisDest,
            hoursFromDest,
            destPortId,
            flightNumFromDest,
            seatFromDest,
            wayDescriptionFromDest,
            hotelId,
            hotelAddress,
            hotelPhone,
            hotelStation,
            wayToHotel
        )
}
