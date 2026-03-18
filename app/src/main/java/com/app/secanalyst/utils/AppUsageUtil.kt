package com.app.secanalyst.utils

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import java.util.Calendar

enum class UsagePeriod { TODAY, YESTERDAY, THIS_WEEK, THIS_MONTH }

data class AppUsageInfo(
    val packageName: String,
    val appName: String,
    val usageTime: Long,
    val lastTimeUsed: Long,
    val appIcon: Drawable? = null
) {
    fun formatUsageTime(): String {
        val s = usageTime / 1000
        val h = s / 3600
        val m = (s % 3600) / 60
        val sec = s % 60
        return when {
            h > 0 -> "${h}小时${m}分钟"
            m > 0 -> "${m}分钟${sec}秒"
            else -> "${sec}秒"
        }
    }

    fun formatLastUsed(): String {
        val diff = System.currentTimeMillis() - lastTimeUsed
        return when {
            diff < 60_000 -> "刚刚"
            diff < 3_600_000 -> "${diff / 60_000}分钟前"
            diff < 86_400_000 -> "${diff / 3_600_000}小时前"
            else -> "${diff / 86_400_000}天前"
        }
    }

    fun percentage(total: Long): Float =
        if (total > 0) (usageTime.toFloat() / total * 100) else 0f
}

object AppUsageUtil {

    private const val SYSTEM_FLAGS =
        ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP

    fun query(context: Context, period: UsagePeriod = UsagePeriod.TODAY): List<AppUsageInfo> {
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val pm = context.packageManager
        val (start, end) = timeRange(period)
        val statsMap = usm.queryAndAggregateUsageStats(start, end)
        val list = mutableListOf<AppUsageInfo>()
        var total = 0L

        for ((pkg, stats) in statsMap) {
            val t = stats.totalTimeInForeground
            if (t <= 0) continue
            total += t
            try {
                val ai = pm.getApplicationInfo(pkg, 0)
                val isSystem = (ai.flags and SYSTEM_FLAGS) != 0
                val name = pm.getApplicationLabel(ai).toString()
                val icon = try { pm.getApplicationIcon(pkg) } catch (_: Exception) { null }
                list.add(
                    AppUsageInfo(
                        packageName = pkg,
                        appName = if (isSystem) "系统: $name" else name,
                        usageTime = t,
                        lastTimeUsed = stats.lastTimeUsed,
                        appIcon = icon
                    )
                )
            } catch (_: PackageManager.NameNotFoundException) {}
        }

        list.add(
            AppUsageInfo(
                packageName = "TOTAL",
                appName = "总使用时间",
                usageTime = total,
                lastTimeUsed = end
            )
        )
        return list.sortedByDescending { it.usageTime }
    }

    private fun timeRange(period: UsagePeriod): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        val now = System.currentTimeMillis()
        return when (period) {
            UsagePeriod.TODAY -> {
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
                cal.timeInMillis to now
            }
            UsagePeriod.YESTERDAY -> {
                cal.add(Calendar.DAY_OF_YEAR, -1)
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
                val s = cal.timeInMillis
                cal.set(Calendar.HOUR_OF_DAY, 23); cal.set(Calendar.MINUTE, 59)
                cal.set(Calendar.SECOND, 59); cal.set(Calendar.MILLISECOND, 999)
                s to cal.timeInMillis
            }
            UsagePeriod.THIS_WEEK -> {
                cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
                cal.timeInMillis to now
            }
            UsagePeriod.THIS_MONTH -> {
                cal.set(Calendar.DAY_OF_MONTH, 1)
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
                cal.timeInMillis to now
            }
        }
    }
}
