package com.app.secanalyst.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.secanalyst.model.NetworkInfo

@Dao
interface NetworkInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(info: NetworkInfo)

    @Query("SELECT * FROM network_info ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatest(): NetworkInfo?
}
