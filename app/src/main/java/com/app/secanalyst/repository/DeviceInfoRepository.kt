package com.app.secanalyst.repository

import android.content.Context
import com.app.secanalyst.database.DatabaseProvider
import com.app.secanalyst.model.DeviceInfoCardPreview
import com.app.secanalyst.utils.DeviceInfoUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DeviceInfoRepository(context: Context) {
    private val appContext = context.applicationContext
    private val database = DatabaseProvider.get(appContext)
    private val networkDao = database.networkInfoDao()
    private val memoryDao = database.memoryInfoDao()
    private val storageDao = database.storageInfoDao()
    private val batteryDao = database.batteryInfoDao()

    suspend fun refreshAll(): Map<String, DeviceInfoCardPreview> {
        val now = System.currentTimeMillis()
        val formatted = formatTime(now)
        runCatching { networkDao.insert(DeviceInfoUtil.getNetworkInfo(appContext, now, formatted)) }
        runCatching { memoryDao.insert(DeviceInfoUtil.getMemoryInfo(appContext, now, formatted)) }
        runCatching { storageDao.insert(DeviceInfoUtil.getStorageInfo(now, formatted)) }
        runCatching { batteryDao.insert(DeviceInfoUtil.getBatteryInfo(appContext, now, formatted)) }
        return loadAllCards()
    }

    suspend fun refreshCard(id: String): DeviceInfoCardPreview? {
        val now = System.currentTimeMillis()
        val formatted = formatTime(now)
        when (id) {
            "basic" -> {}
            "screen" -> {}
            "network" -> runCatching { networkDao.insert(DeviceInfoUtil.getNetworkInfo(appContext, now, formatted)) }
            "memory" -> runCatching { memoryDao.insert(DeviceInfoUtil.getMemoryInfo(appContext, now, formatted)) }
            "storage" -> runCatching { storageDao.insert(DeviceInfoUtil.getStorageInfo(now, formatted)) }
            "battery" -> runCatching { batteryDao.insert(DeviceInfoUtil.getBatteryInfo(appContext, now, formatted)) }
        }
        return buildCard(id)
    }

    suspend fun loadAllCards(): Map<String, DeviceInfoCardPreview> {
        return listOf("basic", "screen", "network", "memory", "storage", "battery")
            .mapNotNull { id -> buildCard(id)?.let { id to it } }
            .toMap()
    }

    private suspend fun buildCard(id: String): DeviceInfoCardPreview? {
        return when (id) {
            "basic" -> {
                val b = DeviceInfoUtil.getBasicInfo()
                DeviceInfoCardPreview(id, "基本信息", listOf(
                    "品牌" to b.brand, "型号" to b.model,
                    "Android 版本" to b.androidVersion, "API 级别" to b.apiLevel.toString(),
                    "处理器架构" to b.arch, "开机时间" to b.uptime,
                    "ROM 发布日期" to b.romReleaseDate
                ))
            }
            "screen" -> {
                val s = DeviceInfoUtil.getScreenInfo(appContext)
                DeviceInfoCardPreview(id, "屏幕信息", listOf(
                    "分辨率" to s.resolution, "密度" to s.density, "DPI" to s.dpi.toString()
                ))
            }
            "network" -> {
                val n = networkDao.getLatest()
                DeviceInfoCardPreview(id, "网络信息", listOf(
                    "连接类型" to (n?.connectionType ?: "-"),
                    "上传速度" to (n?.uploadSpeed ?: "-"),
                    "下载速度" to (n?.downloadSpeed ?: "-"),
                    "更新时间" to (n?.formattedTime ?: "-")
                ))
            }
            "memory" -> {
                val m = memoryDao.getLatest()
                DeviceInfoCardPreview(id, "内存信息", listOf(
                    "总内存" to (m?.total ?: "-"), "已用内存" to (m?.used ?: "-"),
                    "空闲内存" to (m?.free ?: "-"), "内存使用率" to (m?.usageRate ?: "-"),
                    "更新时间" to (m?.formattedTime ?: "-")
                ))
            }
            "storage" -> {
                val s = storageDao.getLatest()
                DeviceInfoCardPreview(id, "存储信息", listOf(
                    "总容量" to (s?.total ?: "-"), "已使用" to (s?.used ?: "-"),
                    "剩余空间" to (s?.free ?: "-"), "使用率" to (s?.usageRate ?: "-"),
                    "更新时间" to (s?.formattedTime ?: "-")
                ))
            }
            "battery" -> {
                val bt = batteryDao.getLatest()
                DeviceInfoCardPreview(id, "电池信息", listOf(
                    "电量" to "${bt?.level ?: 0}%", "温度" to (bt?.temperature ?: "-"),
                    "健康度" to (bt?.health ?: "-"), "电压" to (bt?.voltage ?: "-"),
                    "充电状态" to (bt?.chargingStatus ?: "-"),
                    "更新时间" to (bt?.formattedTime ?: "-")
                ))
            }
            else -> null
        }
    }

    private fun formatTime(timestamp: Long): String {
        return SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.getDefault())
            .format(Date(timestamp))
    }
}
