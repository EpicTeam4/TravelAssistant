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
    @ColumnInfo(name = Schema.FLIGHT) val flightNum: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.SEAT) val seat: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.WAY) val wayDescription: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.HOURS) val hours: Long = 0,
    @ColumnInfo(name = Schema.TIME_DEST) val timeInMillisDest: Long = 0,
    @ColumnInfo(name = Schema.HOURS_DEST) val hoursFromDest: Long = 0,
    @ColumnInfo(name = Schema.PORT_ID_DEST) val destPortId: Int = 0,
    @ColumnInfo(name = Schema.FLIGHT_DEST) val flightNumFromDest: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.SEAT_DEST) val seatFromDest: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.WAY_DEST) val wayDescriptionFromDest: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.HOTEL_ID) val hotelId: Int = 0,
    @ColumnInfo(name = Schema.WAY_HOTEL) val wayToHotel: String = EMPTY_STRING
) {
    object Schema {
        const val TABLE_NAME = "Details"
        const val ID = "id"
        const val CITY_ID = "city_id"
        const val TIME = "timeInMillis"
        const val PORT_ID = "portId"
        const val FLIGHT = "flightNum"
        const val SEAT = "seat"
        const val WAY = "wayDescription"
        const val HOURS = "hours"
        const val TIME_DEST = "timeInMillisDest"
        const val HOURS_DEST = "hoursFromDest"
        const val PORT_ID_DEST = "destPortId"
        const val FLIGHT_DEST = "flightNumFromDest"
        const val SEAT_DEST = "seatFromDest"
        const val WAY_DEST = "wayDescriptionFromDest"
        const val HOTEL_ID = "hotelId"
        const val WAY_HOTEL = "wayToHotel"
    }

    fun copyInfoAboutTravel(
        id: Long = this.id,
        city_id: Long = this.city_id,
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
        wayToHotel: String = this.wayToHotel
    ) = InfoAboutTravel(
            id,
            city_id,
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
            wayToHotel
        )
}