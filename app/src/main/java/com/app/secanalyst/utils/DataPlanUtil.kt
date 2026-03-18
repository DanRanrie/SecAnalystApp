package com.app.secanalyst.utils

import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.net.ConnectivityManager
import java.util.Calendar

object DataPlanUtil {

    fun getMonthlyMobileData(context: Context): Long =
        queryMonthlyData(context, ConnectivityManager.TYPE_MOBILE)

    fun getMonthlyWifiData(context: Context): Long =
        queryMonthlyData(context, ConnectivityManager.TYPE_WIFI)

    private fun queryMonthlyData(context: Context, networkType: Int): Long {
        return try {
            val nsm = context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
            val start = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            val stats = nsm.querySummary(networkType, null, start, System.currentTimeMillis())
            var total = 0L
            val bucket = NetworkStats.Bucket()
            while (stats.hasNextBucket()) {
                stats.getNextBucket(bucket)
                total += bucket.rxBytes + bucket.txBytes
            }
            stats.close()
            total
        } catch (_: Exception) {
            0L
        }
    }

    fun formatBytes(bytes: Long): String = when {
        bytes >= 1024L * 1024 * 1024 -> "%.2f GB".format(bytes / (1024.0 * 1024 * 1024))
        bytes >= 1024L * 1024 -> "%.2f MB".format(bytes / (1024.0 * 1024))
        bytes >= 1024L -> "%.2f KB".format(bytes / 1024.0)
        else -> "$bytes B"
    }
}
