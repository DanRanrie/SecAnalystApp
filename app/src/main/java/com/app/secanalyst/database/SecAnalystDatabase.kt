package com.app.secanalyst.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.secanalyst.database.dao.BatteryInfoDao
import com.app.secanalyst.database.dao.MemoryInfoDao
import com.app.secanalyst.database.dao.NetworkInfoDao
import com.app.secanalyst.database.dao.StorageInfoDao
import com.app.secanalyst.model.BatteryInfo
import com.app.secanalyst.model.MemoryInfo
import com.app.secanalyst.model.NetworkInfo
import com.app.secanalyst.model.StorageInfo

@Database(
    entities = [
        NetworkInfo::class,
        MemoryInfo::class,
        StorageInfo::class,
        BatteryInfo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SecAnalystDatabase : RoomDatabase() {
    abstract fun networkInfoDao(): NetworkInfoDao
    abstract fun memoryInfoDao(): MemoryInfoDao
    abstract fun storageInfoDao(): StorageInfoDao
    abstract fun batteryInfoDao(): BatteryInfoDao
}
