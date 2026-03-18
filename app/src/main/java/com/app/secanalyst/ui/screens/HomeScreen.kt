package com.app.secanalyst.ui.screens

import android.content.Intent
import android.os.SystemClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.secanalyst.ui.activity.IpDetailActivity
import com.app.secanalyst.model.DeviceInfoCardPreview
import com.app.secanalyst.model.PreviewModel
import com.app.secanalyst.model.switch.HomeCardSwitch
import com.app.secanalyst.repository.DeviceInfoRepository
import com.app.secanalyst.ui.compoment.DeviceInfoCard
import com.app.secanalyst.ui.compoment.getDeviceInfoCardIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val ipInfo = PreviewModel.getIpInfoPreview()
    val repository = remember(context) { DeviceInfoRepository(context.applicationContext) }
    val scope = rememberCoroutineScope()

    val cardOrder = listOf("basic", "screen", "network", "memory", "storage", "battery")
    val cardMap = remember { mutableStateMapOf<String, DeviceInfoCardPreview>() }

    var uptime by remember { mutableStateOf(formatUptime(SystemClock.elapsedRealtime())) }

    LaunchedEffect(Unit) {
        val all = repository.refreshAll()
        cardMap.putAll(all)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            uptime = formatUptime(SystemClock.elapsedRealtime())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("首页") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            if (HomeCardSwitch.isEnabled("ip")) {
                DeviceInfoCard(
                    title = "IP 信息",
                    icon = getDeviceInfoCardIcon("ip"),
                    items = ipInfo.toCardItems(),
                    onClick = {
                        val intent = Intent(context, IpDetailActivity::class.java)
                        context.startActivity(intent)
                    },
                    onRefresh = {}
                )
            }
            cardOrder.filter { HomeCardSwitch.isEnabled(it) }.forEach { id ->
                val card = cardMap[id] ?: return@forEach
                val items = if (id == "basic") {
                    card.items.map { (k, v) ->
                        if (k == "开机时间") k to uptime else k to v
                    }
                } else {
                    card.items
                }
                DeviceInfoCard(
                    title = card.title,
                    icon = getDeviceInfoCardIcon(id),
                    items = items,
                    onRefresh = {
                        scope.launch {
                            repository.refreshCard(id)?.let { cardMap[id] = it }
                        }
                    }
                )
            }
        }
    }
}

private fun formatUptime(millis: Long): String {
    val seconds = millis / 1000
    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = seconds % 60
    return String.format("%02d:%02d:%02d", h, m, s)
}
