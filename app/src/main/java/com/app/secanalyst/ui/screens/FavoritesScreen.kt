package com.app.secanalyst.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiFind
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.app.secanalyst.model.PreviewModel
import com.app.secanalyst.model.switch.ToolboxModuleSwitch
import com.app.secanalyst.ui.compoment.ToolboxCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {
    val items = PreviewModel.getToolboxItems().filter { isToolboxItemEnabled(it.id) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("工具箱") })
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = true
        ) {
            items(items) { item ->
                ToolboxCard(
                    icon = getToolboxIcon(item.id),
                    title = item.title,
                    subtitle = item.subtitle,
                    onClick = getToolboxOnClick(item.id)
                )
            }
        }
    }
}

private fun isToolboxItemEnabled(id: String): Boolean = when (id) {
    "traffic" -> ToolboxModuleSwitch.trafficMonitor
    "appUsage" -> ToolboxModuleSwitch.appUsage
    "networkDiagnosis" -> ToolboxModuleSwitch.networkDiagnosis
    "dataPlan" -> ToolboxModuleSwitch.dataPlan
    "deviceInfo" -> ToolboxModuleSwitch.deviceInfo
    "wifiScan" -> ToolboxModuleSwitch.wifiScan
    "alertCenter" -> ToolboxModuleSwitch.alertCenter
    else -> true
}

private fun getToolboxIcon(id: String): ImageVector = when (id) {
    "traffic" -> Icons.Default.Traffic
    "appUsage" -> Icons.Default.Apps
    "networkDiagnosis" -> Icons.Default.NetworkCheck
    "dataPlan" -> Icons.Default.DataUsage
    "deviceInfo" -> Icons.Default.PhoneAndroid
    "wifiScan" -> Icons.Default.WifiFind
    "alertCenter" -> Icons.Default.Warning
    else -> Icons.Default.Traffic
}

private fun getToolboxOnClick(id: String): () -> Unit = when (id) {
    "traffic" -> ({ if (ToolboxModuleSwitch.trafficMonitor) {} })
    "appUsage" -> ({ if (ToolboxModuleSwitch.appUsage) {} })
    "networkDiagnosis" -> ({ if (ToolboxModuleSwitch.networkDiagnosis) {} })
    "dataPlan" -> ({ if (ToolboxModuleSwitch.dataPlan) {} })
    "deviceInfo" -> ({ if (ToolboxModuleSwitch.deviceInfo) {} })
    "wifiScan" -> ({ if (ToolboxModuleSwitch.wifiScan) {} })
    "alertCenter" -> ({ if (ToolboxModuleSwitch.alertCenter) {} })
    else -> ({})
}
