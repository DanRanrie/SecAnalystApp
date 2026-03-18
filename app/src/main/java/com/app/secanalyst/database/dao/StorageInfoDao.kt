package com.app.secanalyst.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.secanalyst.model.StorageInfo

@Dao
interface StorageInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(info: StorageInfo)

    @Query("SELECT * FROM storage_info ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatest(): StorageInfo?
}
