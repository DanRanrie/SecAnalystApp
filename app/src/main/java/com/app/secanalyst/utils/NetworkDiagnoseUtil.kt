package com.app.secanalyst.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

data class DiagnoseResult(
    val wifiName: String,
    val connectionType: String,
    val signalStrength: String,
    val connectivity: String,
    val downloadSpeed: String,
    val uploadSpeed: String,
    val latency: String,
    val stability: String,
    val jitter: String
)

data class PacketLossResult(
    val avgLoss: String,
    val details: List<Pair<String, String>>
)

object NetworkDiagnoseUtil {

    private const val PING_HOST = "www.baidu.com"
    private const val PING_COUNT = 4

    @SuppressLint("MissingPermission")
    suspend fun diagnose(context: Context): DiagnoseResult = withContext(Dispatchers.IO) {
        val connType = getConnectionType(context)
        val wifiName = getWifiName(context)
        val signal = getSignalStrength(context)
        val download = NetworkSpeedTestUtil.downloadSpeed()
        val upload = NetworkSpeedTestUtil.uploadSpeed(context)
        val latency = measureLatency()
        val jitter = measureJitter()
        val connectivity = if (download > 0) "连通" else "异常"
        val stability = assessStability(signal, jitter)
        DiagnoseResult(
            wifiName = wifiName,
            connectionType = connType,
            signalStrength = "$signal dBm",
            connectivity = connectivity,
            downloadSpeed = "%.2f Mbps".format(download),
            uploadSpeed = "%.2f Mbps".format(upload),
            latency = "$latency ms",
            stability = stability,
            jitter = "$jitter ms"
        )
    }

    suspend fun packetLossTest(): PacketLossResult = withContext(Dispatchers.IO) {
        val counts = listOf(4, 8, 12, 16, 20)
        val details = mutableListOf<Pair<String, String>>()
        var totalLoss = 0f

        for (count in counts) {
            val (transmitted, received) = pingDetail(count)
            val loss = if (transmitted > 0) (transmitted - received) * 100f / transmitted else 0f
            details.add("${count}次发送" to "成功${received}次, 失败${transmitted - received}次")
            totalLoss += loss
        }

        val avg = "${(totalLoss / counts.size).roundToInt()}%"
        PacketLossResult(avg, details)
    }

    @SuppressLint("MissingPermission")
    private fun getConnectionType(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return "未连接"
        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> getWifiDetail(context)
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "移动数据"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "有线网络"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> "VPN"
            else -> "未知"
        }
    }

    @SuppressLint("MissingPermission")
    private fun getWifiDetail(context: Context): String {
        return try {
            val wm = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wm.connectionInfo
            val security = when (info.currentSecurityType) {
                WifiInfo.SECURITY_TYPE_OPEN -> "开放"
                WifiInfo.SECURITY_TYPE_WEP -> "WEP"
                WifiInfo.SECURITY_TYPE_PSK -> "WPA/WPA2"
                WifiInfo.SECURITY_TYPE_SAE -> "WPA3"
                WifiInfo.SECURITY_TYPE_EAP -> "EAP"
                else -> "未知"
            }
            val band = when {
                info.frequency in 2401..2499 -> "2.4GHz"
                info.frequency in 4901..5899 -> "5GHz"
                info.frequency > 5900 -> "6GHz"
                else -> ""
            }
            "WiFi ($security, $band)"
        } catch (_: Exception) {
            "WiFi"
        }
    }

    @SuppressLint("MissingPermission")
    private fun getWifiName(context: Context): String {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val caps = cm.getNetworkCapabilities(cm.activeNetwork)
            if (caps == null || !caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return "非WiFi"
            val wm = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            var ssid = wm.connectionInfo.ssid
            if (ssid == "<unknown ssid>") return "未知WiFi"
            if (ssid.startsWith("\"") && ssid.endsWith("\"")) ssid = ssid.substring(1, ssid.length - 1)
            ssid
        } catch (_: Exception) {
            "未知"
        }
    }

    private fun getSignalStrength(context: Context): Int {
        return try {
            val wm = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wm.connectionInfo.rssi
        } catch (_: Exception) {
            -100
        }
    }

    private fun measureLatency(): Int {
        var total = 0
        repeat(PING_COUNT) { total += ping(PING_HOST) }
        return total / PING_COUNT
    }

    private fun measureJitter(): Int {
        val latencies = (1..PING_COUNT).map { ping(PING_HOST) }
        val mean = latencies.average()
        val variance = latencies.sumOf { (it - mean).pow(2) } / latencies.size
        return sqrt(variance).roundToInt()
    }

    private fun assessStability(rssi: Int, jitter: Int): String = when {
        rssi > -60 && jitter < 30 -> "优"
        rssi > -70 && jitter < 50 -> "良"
        else -> "差"
    }

    private fun ping(host: String): Int {
        return try {
            val process = ProcessBuilder("/system/bin/ping", "-c", "1", host).start()
            val output = BufferedReader(InputStreamReader(process.inputStream)).readText()
            val exitCode = process.waitFor()
            if (exitCode == 0) {
                val regex = """time=(\d+(\.\d+)?)\s*ms""".toRegex()
                regex.find(output)?.groupValues?.get(1)?.toFloat()?.roundToInt() ?: 999
            } else 999
        } catch (_: Exception) {
            999
        }
    }

    private fun pingDetail(count: Int): Pair<Int, Int> {
        return try {
            val process = ProcessBuilder("/system/bin/ping", "-c", count.toString(), PING_HOST).start()
            val output = BufferedReader(InputStreamReader(process.inputStream)).readText()
            process.waitFor()
            val regex = """(\d+)\s+packets transmitted,\s+(\d+)\s+received""".toRegex()
            val match = regex.find(output)
            if (match != null) Pair(match.groupValues[1].toInt(), match.groupValues[2].toInt())
            else Pair(count, 0)
        } catch (_: Exception) {
            Pair(count, 0)
        }
    }
}
