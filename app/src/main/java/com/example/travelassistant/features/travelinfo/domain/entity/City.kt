package com.example.travelassistant.features.travelinfo.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * City - сущность базы данных
 *
 * @property id - идентификатор города
 * @property name - наименование города
 * @property description - краткое описание
 * @property timezone - часовой пояс
 * @property latitude - широта
 * @property longitude - долгота
 * @property image - ссылка на изображение
 * @property slug - краткое наименование
 *
 * @author Marianne Sabanchieva
 */

@Entity(tableName = City.Schema.TABLE_NAME)
class City(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Schema.ID) val id: Long = 0,
    @ColumnInfo(name = Schema.NAME) val name: String = "",
    @ColumnInfo(name = Schema.DESCRIPTION) val description: String? = "",
    @ColumnInfo(name = Schema.TIMEZONE) val timezone: String? = "",
    @ColumnInfo(name = Schema.LAT) var latitude: Double?,
    @ColumnInfo(name = Schema.LON) var longitude: Double?,
    @ColumnInfo(name = Schema.IMAGE) val image: String? = "",
    @ColumnInfo(name = Schema.SLUG) val slug: String? = ""
) {
    object Schema {
        const val TABLE_NAME = "cities"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val TIMEZONE = "timezone"
        const val LAT = "lat"
        const val LON = "lon"
        const val IMAGE = "image"
        const val SLUG = "slug"
    }
}