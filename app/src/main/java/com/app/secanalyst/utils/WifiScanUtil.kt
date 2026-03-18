package com.app.secanalyst.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager

object WifiScanUtil {

    private var wifiManager: WifiManager? = null

    fun init(context: Context) {
        wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    @SuppressLint("MissingPermission")
    fun startScan(): Boolean = wifiManager?.startScan() ?: false

    @SuppressLint("MissingPermission")
    fun getScanResults(): List<ScanResult> = wifiManager?.scanResults ?: emptyList()

    fun isWifiEnabled(): Boolean = wifiManager?.isWifiEnabled ?: false
}
