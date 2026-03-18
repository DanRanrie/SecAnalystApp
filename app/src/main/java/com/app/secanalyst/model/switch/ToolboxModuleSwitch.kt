package com.app.secanalyst.model.switch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ToolboxModuleSwitch {
    var trafficMonitor by mutableStateOf(true)
    var appUsage by mutableStateOf(true)
    var networkDiagnosis by mutableStateOf(true)
    var dataPlan by mutableStateOf(true)
    var deviceInfo by mutableStateOf(true)
    var wifiScan by mutableStateOf(true)
    var alertCenter by mutableStateOf(true)
}
