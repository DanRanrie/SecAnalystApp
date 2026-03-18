package com.app.secanalyst.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var instance: SecAnalystDatabase? = null

    fun get(context: Context): SecAnalystDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                SecAnalystDatabase::class.java,
                "sec_analyst.db"
            ).build().also { instance = it }
        }
    }
}
