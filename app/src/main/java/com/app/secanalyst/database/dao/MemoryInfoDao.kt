package com.app.secanalyst.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.secanalyst.model.MemoryInfo

@Dao
interface MemoryInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(info: MemoryInfo)

    @Query("SELECT * FROM memory_info ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatest(): MemoryInfo?
}
