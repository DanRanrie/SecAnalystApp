package com.app.secanalyst.utils

import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.RemoteException
import java.util.Calendar

enum class TrafficCycle { TODAY, YESTERDAY, THIS_MONTH, LAST_MONTH }
enum class TrafficType { MOBILE, WIFI }

data class AppTrafficInfo(
    val uid: Int,
    val appName: String,
    val rxBytes: Long,
    val txBytes: Long,
    val appIcon: Drawable? = null
) {
    val totalBytes: Long get() = rxBytes + txBytes

    fun formatTotal(): String = formatBytes(totalBytes)
    fun formatRx(): String = formatBytes(rxBytes)
    fun formatTx(): String = formatBytes(txBytes)

    private fun formatBytes(bytes: Long): String = when {
        bytes > 1024 * 1024 * 1024 -> String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0))
        bytes > 1024 * 1024 -> String.format("%.2f MB", bytes / (1024.0 * 1024.0))
        bytes > 1024 -> String.format("%.2f KB", bytes / 1024.0)
        else -> "$bytes B"
    }
}

object TrafficMonitorUtil {

    private const val SYSTEM_APP_FLAGS =
        ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP

    fun queryTraffic(
        context: Context,
        cycle: TrafficCycle = TrafficCycle.TODAY,
        type: TrafficType = TrafficType.MOBILE
    ): List<AppTrafficInfo> {
        val nsm = context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
        val pm = context.packageManager
        val (start, end) = getTimeRange(cycle)
        val netType = if (type == TrafficType.MOBILE) ConnectivityManager.TYPE_MOBILE else ConnectivityManager.TYPE_WIFI

        val result = mutableListOf<AppTrafficInfo>()
        var totalRx = 0L
        var totalTx = 0L

        for (app in pm.getInstalledApplications(0)) {
            try {
                var rx = 0L
                var tx = 0L
                val stats = nsm.queryDetailsForUid(netType, null, start, end, app.uid)
                val bucket = NetworkStats.Bucket()
                while (stats.hasNextBucket()) {
                    stats.getNextBucket(bucket)
                    rx += bucket.rxBytes
                    tx += bucket.txBytes
                }
                stats.close()

                if (rx > 0 || tx > 0) {
                    totalRx += rx
                    totalTx += tx
                    val isSystem = (app.flags and SYSTEM_APP_FLAGS) != 0
                    val name = try {
                        pm.getApplicationLabel(pm.getApplicationInfo(app.packageName, 0)).toString()
                    } catch (_: Exception) {
                        app.packageName
                    }
                    val icon = try {
                        pm.getApplicationIcon(app.packageName)
                    } catch (_: PackageManager.NameNotFoundException) {
                        null
                    }
                    result.add(
                        AppTrafficInfo(
                            uid = app.uid,
                            appName = if (isSystem) "系统: $name" else name,
                            rxBytes = rx,
                            txBytes = tx,
                            appIcon = icon
                        )
                    )
                }
            } catch (_: RemoteException) {
            } catch (_: Exception) {
            }
        }

        result.add(AppTrafficInfo(uid = -1, appName = "总计", rxBytes = totalRx, txBytes = totalTx))
        return result.sortedByDescending { it.totalBytes }
    }

    private fun getTimeRange(cycle: TrafficCycle): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        val now = System.currentTimeMillis()
        return when (cycle) {
            TrafficCycle.TODAY -> {
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
                cal.timeInMillis to now
            }
            TrafficCycle.YESTERDAY -> {
                cal.add(Calendar.DAY_OF_YEAR, -1)
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
                val s = cal.timeInMillis
                cal.set(Calendar.HOUR_OF_DAY, 23); cal.set(Calendar.MINUTE, 59)
                cal.set(Calendar.SECOND, 59); cal.set(Calendar.MILLISECOND, 999)
                s to cal.timeInMillis
            }
            TrafficCycle.THIS_MONTH -> {
                cal.set(Calendar.DAY_OF_MONTH, 1)
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
                cal.timeInMillis to now
            }
            TrafficCycle.LAST_MONTH -> {
                cal.add(Calendar.MONTH, -1)
                cal.set(Calendar.DAY_OF_MONTH, 1)
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
                val s = cal.timeInMillis
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                cal.set(Calendar.HOUR_OF_DAY, 23); cal.set(Calendar.MINUTE, 59)
                cal.set(Calendar.SECOND, 59); cal.set(Calendar.MILLISECOND, 999)
                s to cal.timeInMillis
            }
        }
    }
}
