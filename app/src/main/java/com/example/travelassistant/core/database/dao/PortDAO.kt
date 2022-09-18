package com.example.travelassistant.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.travelassistant.features.travelinfo.domain.entity.Port

@Dao
interface PortDAO {
    @Query("Select * From ${Port.Schema.TABLE_NAME}")
    fun getAllPorts(): List<Port>

    @Query("Select * From ${Port.Schema.TABLE_NAME} WHERE ${Port.Schema.ID} = :id")
    fun getPortById(id: Long): Port
}