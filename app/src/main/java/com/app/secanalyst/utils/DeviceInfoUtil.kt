package com.app.secanalyst.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.WindowManager
import com.app.secanalyst.model.BasicInfo
import com.app.secanalyst.model.BatteryInfo
import com.app.secanalyst.model.MemoryInfo
import com.app.secanalyst.model.NetworkInfo
import com.app.secanalyst.model.ScreenInfo
import com.app.secanalyst.model.StorageInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

object DeviceInfoUtil {

    fun getBasicInfo(): BasicInfo {
        return BasicInfo(
            brand = Build.BRAND,
            model = Build.MODEL,
            androidVersion = Build.VERSION.RELEASE ?: "",
            apiLevel = Build.VERSION.SDK_INT,
            arch = System.getProperty("os.arch") ?: "",
            uptime = formatBootTime(SystemClock.elapsedRealtime()),
            romReleaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(Build.TIME))
        )
    }

    fun getScreenInfo(context: Context): ScreenInfo {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        val density = when (displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> "LDPI"
            DisplayMetrics.DENSITY_MEDIUM -> "MDPI"
            DisplayMetrics.DENSITY_HIGH -> "HDPI"
            DisplayMetrics.DENSITY_XHIGH -> "XHDPI"
            DisplayMetrics.DENSITY_XXHIGH -> "XXHDPI"
            DisplayMetrics.DENSITY_XXXHIGH -> "XXXHDPI"
            else -> "Unknown"
        }

        return ScreenInfo(
            resolution = "${width}x${height}",
            density = density,
            dpi = displayMetrics.densityDpi
        )
    }

    @SuppressLint("MissingPermission")
    fun getNetworkInfo(context: Context, now: Long, formattedTime: String): NetworkInfo {
        val connectionType = try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            when {
                capabilities == null -> "Disconnected"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Mobile Data"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
                else -> "Unknown"
            }
        } catch (_: SecurityException) {
            "No Permission"
        } catch (_: Throwable) {
            "Unknown"
        }

        return NetworkInfo(
            connectionType = connectionType,
            uploadSpeed = "-",
            downloadSpeed = "-",
            timestamp = now,
            formattedTime = formattedTime
        )
    }

    fun getMemoryInfo(context: Context, now: Long, formattedTime: String): MemoryInfo {
        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val memoryInfo = android.app.ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val totalGB = memoryInfo.totalMem / (1024 * 1024 * 1024)
        val freeGB = memoryInfo.availMem / (1024 * 1024 * 1024)
        val usedGB = totalGB - freeGB
        val percent = ((usedGB.toDouble() / totalGB.toDouble()) * 100).roundToInt()

        return MemoryInfo(
            total = "${totalGB}GB",
            used = "${usedGB}GB",
            free = "${freeGB}GB",
            usageRate = "${percent}%",
            timestamp = now,
            formattedTime = formattedTime
        )
    }

    fun getStorageInfo(now: Long, formattedTime: String): StorageInfo {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalGB = (totalBlocks * blockSize) / (1024 * 1024 * 1024)
        val freeGB = (availableBlocks * blockSize) / (1024 * 1024 * 1024)
        val usedGB = totalGB - freeGB
        val percent = ((usedGB.toDouble() / totalGB.toDouble()) * 100).roundToInt()

        return StorageInfo(
            total = "${totalGB}GB",
            used = "${usedGB}GB",
            free = "${freeGB}GB",
            usageRate = "${percent}%",
            timestamp = now,
            formattedTime = formattedTime
        )
    }

    @SuppressLint("MissingPermission")
    fun getBatteryInfo(context: Context, now: Long, formattedTime: String): BatteryInfo {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val intent = context.registerReceiver(
            null,
            android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED)
        )

        val level = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        val temperature =
            intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)?.div(10f) ?: 0f
        val voltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0

        val health = when (
            intent?.getIntExtra(
                BatteryManager.EXTRA_HEALTH,
                BatteryManager.BATTERY_HEALTH_UNKNOWN
            )
        ) {
            BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
            BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified Failure"
            BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
            else -> "Unknown"
        }

        val status = intent?.getIntExtra(
            BatteryManager.EXTRA_STATUS,
            BatteryManager.BATTERY_STATUS_UNKNOWN
        )
        val charging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
            status == BatteryManager.BATTERY_STATUS_FULL

        val chargingStatus = if (!charging) {
            "未充电"
        } else {
            val plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            when (plug) {
                BatteryManager.BATTERY_PLUGGED_USB -> "USB充电"
                BatteryManager.BATTERY_PLUGGED_AC -> "AC充电"
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> "无线充电"
                else -> "充电中"
            }
        }

        return BatteryInfo(
            level = level,
            temperature = "${temperature}°C",
            health = health,
            voltage = "${voltage}mV",
            chargingStatus = chargingStatus,
            timestamp = now,
            formattedTime = formattedTime
        )
    }

    private fun formatBootTime(millis: Long): String {
        val seconds = millis / 1000
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}
