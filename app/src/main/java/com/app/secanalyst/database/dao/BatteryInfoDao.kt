package com.app.secanalyst.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.secanalyst.model.BatteryInfo

@Dao
interface BatteryInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(info: BatteryInfo)

    @Query("SELECT * FROM battery_info ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatest(): BatteryInfo?
}
