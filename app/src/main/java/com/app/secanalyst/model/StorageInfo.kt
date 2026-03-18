package com.app.secanalyst.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "storage_info")
data class StorageInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val total: String,
    val used: String,
    val free: String,
    val usageRate: String,
    val timestamp: Long,
    val formattedTime: String
)
