package com.app.secanalyst.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_info")
data class NetworkInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val connectionType: String,
    val uploadSpeed: String,
    val downloadSpeed: String,
    val timestamp: Long,
    val formattedTime: String
)
