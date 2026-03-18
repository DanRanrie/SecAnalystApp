package com.app.secanalyst.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.secanalyst.model.BasicInfo
import com.app.secanalyst.model.BatteryInfo
import com.app.secanalyst.model.MemoryInfo
import com.app.secanalyst.model.NetworkInfo
import com.app.secanalyst.model.ScreenInfo
import com.app.secanalyst.model.StorageInfo
import com.app.secanalyst.utils.DeviceInfoUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private data class AllDeviceInfo(
    val basic: BasicInfo,
    val screen: ScreenInfo,
    val network: NetworkInfo,
    val memory: MemoryInfo,
    val storage: StorageInfo,
    val battery: BatteryInfo
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DeviceInfoDetailScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    var info by remember { mutableStateOf<AllDeviceInfo?>(null) }
    var expanded by remember { mutableStateOf(setOf("basic")) }

    LaunchedEffect(Unit) {
        info = withContext(Dispatchers.IO) {
            val now = System.currentTimeMillis()
            val fmt = java.text.SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", java.util.Locale.getDefault())
                .format(java.util.Date(now))
            AllDeviceInfo(
                basic = DeviceInfoUtil.getBasicInfo(),
                screen = DeviceInfoUtil.getScreenInfo(context),
                network = DeviceInfoUtil.getNetworkInfo(context, now, fmt),
                memory = DeviceInfoUtil.getMemoryInfo(context, now, fmt),
                storage = DeviceInfoUtil.getStorageInfo(now, fmt),
                battery = DeviceInfoUtil.getBatteryInfo(context, now, fmt)
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设备信息") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        if (info == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularWavyProgressIndicator(modifier = Modifier.size(48.dp))
            }
        } else {
            val data = info!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                ExpandableSection("基本信息", expanded.contains("basic"), { expanded = toggle(expanded, "basic") }) {
                    InfoRow("品牌", data.basic.brand)
                    InfoRow("型号", data.basic.model)
                    InfoRow("Android版本", data.basic.androidVersion)
                    InfoRow("API级别", data.basic.apiLevel.toString())
                    InfoRow("处理器架构", data.basic.arch)
                    InfoRow("开机时间", data.basic.uptime)
                    InfoRow("ROM发布日期", data.basic.romReleaseDate)
                }

                ExpandableSection("屏幕信息", expanded.contains("screen"), { expanded = toggle(expanded, "screen") }) {
                    InfoRow("分辨率", data.screen.resolution)
                    InfoRow("密度", data.screen.density)
                    InfoRow("DPI", data.screen.dpi.toString())
                }

                ExpandableSection("网络信息", expanded.contains("network"), { expanded = toggle(expanded, "network") }) {
                    InfoRow("连接类型", data.network.connectionType)
                    InfoRow("上传速度", data.network.uploadSpeed)
                    InfoRow("下载速度", data.network.downloadSpeed)
                }

                ExpandableSection("内存信息", expanded.contains("memory"), { expanded = toggle(expanded, "memory") }) {
                    InfoRow("总内存", data.memory.total)
                    InfoRow("已用内存", data.memory.used)
                    InfoRow("空闲内存", data.memory.free)
                    InfoRow("使用率", data.memory.usageRate)
                    val memPercent = data.memory.usageRate.removeSuffix("%").toFloatOrNull() ?: 0f
                    LinearProgressIndicator(
                        progress = { memPercent / 100f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = when {
                            memPercent > 80 -> Color.Red
                            memPercent > 60 -> Color.Yellow
                            else -> Color.Green
                        }
                    )
                }

                ExpandableSection("存储信息", expanded.contains("storage"), { expanded = toggle(expanded, "storage") }) {
                    InfoRow("总容量", data.storage.total)
                    InfoRow("已使用", data.storage.used)
                    InfoRow("剩余空间", data.storage.free)
                    InfoRow("使用率", data.storage.usageRate)
                    val storPercent = data.storage.usageRate.removeSuffix("%").toFloatOrNull() ?: 0f
                    LinearProgressIndicator(
                        progress = { storPercent / 100f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = when {
                            storPercent > 90 -> Color.Red
                            storPercent > 70 -> Color.Yellow
                            else -> Color.Green
                        }
                    )
                }

                ExpandableSection("电池信息", expanded.contains("battery"), { expanded = toggle(expanded, "battery") }) {
                    InfoRow("电量", "${data.battery.level}%")
                    InfoRow("温度", data.battery.temperature)
                    InfoRow("健康度", data.battery.health)
                    InfoRow("电压", data.battery.voltage)
                    InfoRow("充电状态", data.battery.chargingStatus)
                    LinearProgressIndicator(
                        progress = { data.battery.level / 100f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = when {
                            data.battery.level < 20 -> Color.Red
                            data.battery.level < 50 -> Color.Yellow
                            else -> Color.Green
                        }
                    )
                }
            }
        }
    }
}

private fun toggle(set: Set<String>, key: String): Set<String> =
    if (set.contains(key)) set - key else set + key

@Composable
private fun ExpandableSection(
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onToggle() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Icon(
                    if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                content()
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        Text(value, fontWeight = FontWeight.Medium)
    }
}
