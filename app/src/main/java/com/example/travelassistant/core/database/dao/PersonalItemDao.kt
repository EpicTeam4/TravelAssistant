package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travelassistant.core.domain.entity.PersonalItem

@Dao
interface PersonalItemDao {
    @Query("Select * From ${PersonalItem.Schema.TABLE_NAME}")
    suspend fun getAllItems(): List<PersonalItem>

    @Query("Delete From ${PersonalItem.Schema.TABLE_NAME}")
    suspend fun deleteAllItems()

    @Query("Delete From ${PersonalItem.Schema.TABLE_NAME} Where ${PersonalItem.Schema.ID} = :id")
    suspend fun deleteItemById(id: Int)

    @Query("UPDATE ${PersonalItem.Schema.TABLE_NAME} SET ${PersonalItem.Schema.ITEM} = :item, ${PersonalItem.Schema.ITEM_COUNT} = :count WHERE ${PersonalItem.Schema.ID} = :id")
    suspend fun updatePersonalItem(id: Int, item: String, count: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PersonalItem)
}