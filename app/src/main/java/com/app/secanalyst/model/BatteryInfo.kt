package com.app.secanalyst.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery_info")
data class BatteryInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val level: Int,
    val temperature: String,
    val health: String,
    val voltage: String,
    val chargingStatus: String,
    val timestamp: Long,
    val formattedTime: String
)
