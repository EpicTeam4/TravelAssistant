package com.example.travelassistant.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Personal item - сущность базы данных
 *
 * @property id - идентификатор
 * @property item - наименование
 * @property item_count - количество
 *
 * @author Marianne Sabanchieva
 */

@Entity(tableName = PersonalItem.Schema.TABLE_NAME)
data class PersonalItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Schema.ID) val id: Int = 0,
    @ColumnInfo(name = Schema.ITEM) val item: String = "",
    @ColumnInfo(name = Schema.ITEM_COUNT) val item_count: Int? = 0
) {
    object Schema {
        const val TABLE_NAME = "items"
        const val ID = "id"
        const val ITEM = "item"
        const val ITEM_COUNT = "item_count"
    }

    fun copyItem(item: String = this.item, item_count: Int? = this.item_count): PersonalItem =
        PersonalItem(id, item, item_count)
}