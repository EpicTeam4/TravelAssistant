package com.example.travelassistant.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travelassistant.core.Constants.EMPTY_STRING

@Entity(tableName = Sights.Schema.TABLE_NAME)
data class Sights(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Schema.ID) val id: Int = 0,
    @ColumnInfo(name = Schema.NAME) val name: String = EMPTY_STRING,
    @ColumnInfo(name = Schema.DESCRIPTION) val description: String? = EMPTY_STRING,
    @ColumnInfo(name = Schema.IMAGE) val image: String? = EMPTY_STRING,
    @ColumnInfo(name = Schema.SLUG) val slug: String? = EMPTY_STRING
) {
    object Schema {
        const val TABLE_NAME = "Places"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
        const val SLUG = "slug"
    }
}
