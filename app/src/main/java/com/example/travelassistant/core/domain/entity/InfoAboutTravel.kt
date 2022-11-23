package com.example.travelassistant.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travelassistant.core.Constants.EMPTY_STRING

@Entity(tableName = InfoAboutTravel.Schema.TABLE_NAME)
data class InfoAboutTravel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Schema.ID) val id: Long = 0,
    @ColumnInfo(name = Schema.CITY_ID) val city_id: Long = 0,
    @ColumnInfo(name = Schema.TIME) val timeInMillis: Long = 0,
    @ColumnInfo(name = Schema.PORT_ID) val portId: Int = 0,
    @ColumnInfo(name = Schema.PORT_TYPE) val portType: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.FLIGHT) val flightNum: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.SEAT) val seat: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.WAY) val wayDescription: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.HOURS) val hours: Long = 0,
    @ColumnInfo(name = Schema.TIME_DEST) val timeInMillisDest: Long = 0,
    @ColumnInfo(name = Schema.HOURS_DEST) val hoursFromDest: Long = 0,
    @ColumnInfo(name = Schema.PORT_ID_DEST) val destPortId: Int = 0,
    @ColumnInfo(name = Schema.DEST_PORT_TYPE) val destPortType: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.FLIGHT_DEST) val flightNumFromDest: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.SEAT_DEST) val seatFromDest: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.WAY_DEST) val wayDescriptionFromDest: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.HOTEL_ID) val hotelId: Int = 0,
    @ColumnInfo(name = Schema.HOTEL_NAME) val hotelName: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.HOTEL_ADDRESS) val hotelAddress: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.HOTEL_PHONE) val hotelPhone: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.HOTEL_SUBWAY) val hotelSubway: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.WAY_HOTEL) val wayToHotel: String = EMPTY_STRING
) {
    object Schema {
        const val TABLE_NAME = "Details"
        const val ID = "id"
        const val CITY_ID = "city_id"
        const val TIME = "timeInMillis"
        const val PORT_ID = "portId"
        const val PORT_TYPE = "portType"
        const val FLIGHT = "flightNum"
        const val SEAT = "seat"
        const val WAY = "wayDescription"
        const val HOURS = "hours"
        const val TIME_DEST = "timeInMillisDest"
        const val HOURS_DEST = "hoursFromDest"
        const val PORT_ID_DEST = "destPortId"
        const val DEST_PORT_TYPE = "destPortType"
        const val FLIGHT_DEST = "flightNumFromDest"
        const val SEAT_DEST = "seatFromDest"
        const val WAY_DEST = "wayDescriptionFromDest"
        const val HOTEL_ID = "hotelId"
        const val HOTEL_NAME = "hotelName"
        const val HOTEL_ADDRESS = "hotelAddress"
        const val HOTEL_PHONE = "hotelPhone"
        const val HOTEL_SUBWAY = "hotelSubway"
        const val WAY_HOTEL = "wayToHotel"
    }

    fun copyInfoAboutTravel(
        id: Long = this.id,
        city_id: Long = this.city_id,
        timeInMillis: Long = this.timeInMillis,
        portId: Int = this.portId,
        portType: String = this.portType,
        flightNum: String = this.flightNum,
        seat: String = this.seat,
        wayDescription: String = this.wayDescription,
        hours: Long = this.hours,
        timeInMillisDest: Long = this.timeInMillisDest,
        hoursFromDest: Long = this.hoursFromDest,
        destPortId: Int = this.destPortId,
        destPortType: String = this.destPortType,
        flightNumFromDest: String = this.flightNumFromDest,
        seatFromDest: String = this.seatFromDest,
        wayDescriptionFromDest: String = this.wayDescriptionFromDest,
        hotelId: Int = this.hotelId,
        hotelName: String = this.hotelName,
        hotelAddress: String = this.hotelAddress,
        hotelPhone: String = this.hotelPhone,
        hotelSubway: String = this.hotelSubway,
        wayToHotel: String = this.wayToHotel
    ) = InfoAboutTravel(
            id,
            city_id,
            timeInMillis,
            portId,
            portType,
            flightNum,
            seat,
            wayDescription,
            hours,
            timeInMillisDest,
            hoursFromDest,
            destPortId,
            destPortType,
            flightNumFromDest,
            seatFromDest,
            wayDescriptionFromDest,
            hotelId,
            hotelName,
            hotelAddress,
            hotelPhone,
            hotelSubway,
            wayToHotel
        )
}