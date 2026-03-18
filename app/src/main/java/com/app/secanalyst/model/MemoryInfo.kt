package com.app.secanalyst.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory_info")
data class MemoryInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val total: String,
    val used: String,
    val free: String,
    val usageRate: String,
    val timestamp: Long,
    val formattedTime: String
)
