package com.app.secanalyst.ui.screens

import android.content.Intent
import android.provider.Settings
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.runtime.DisposableEffect
import com.app.secanalyst.ui.compoment.AppIconImage
import com.app.secanalyst.utils.AppTrafficInfo
import com.app.secanalyst.utils.PermissionUtil
import com.app.secanalyst.utils.TrafficCycle
import com.app.secanalyst.utils.TrafficMonitorUtil
import com.app.secanalyst.utils.TrafficType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TrafficMonitorScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasPermission by remember { mutableStateOf(PermissionUtil.hasUsageStatsPermission(context)) }
    var showDialog by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf<List<AppTrafficInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var cycle by remember { mutableStateOf(TrafficCycle.TODAY) }
    var type by remember { mutableStateOf(TrafficType.MOBILE) }
    var cycleExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasPermission = PermissionUtil.hasUsageStatsPermission(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(hasPermission) {
        if (!hasPermission) {
            showDialog = true
            isLoading = false
        }
    }

    if (showDialog && !hasPermission) {
        AlertDialog(
            onDismissRequest = { showDialog = false; onBack() },
            title = { Text("需要权限") },
            text = { Text("流量监控需要\"使用情况访问\"权限，请在设置中找到本应用并启用。") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                }) { Text("去授权") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false; onBack() }) { Text("取消") }
            }
        )
    }

    val cycleLabel = when (cycle) {
        TrafficCycle.TODAY -> "今日"
        TrafficCycle.YESTERDAY -> "昨日"
        TrafficCycle.THIS_MONTH -> "当月"
        TrafficCycle.LAST_MONTH -> "上月"
    }
    val typeLabel = if (type == TrafficType.MOBILE) "流量" else "WiFi"

    fun load() {
        if (!hasPermission) return
        scope.launch {
            isLoading = true
            data = withContext(Dispatchers.IO) {
                TrafficMonitorUtil.queryTraffic(context, cycle, type)
            }
            isLoading = false
        }
    }

    LaunchedEffect(cycle, type, hasPermission) {
        if (hasPermission) load()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("流量监控") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { load() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "刷新")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    TextButton(onClick = { cycleExpanded = true }) {
                        Text(cycleLabel)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(expanded = cycleExpanded, onDismissRequest = { cycleExpanded = false }) {
                        listOf(
                            TrafficCycle.TODAY to "今日", TrafficCycle.YESTERDAY to "昨日",
                            TrafficCycle.THIS_MONTH to "当月", TrafficCycle.LAST_MONTH to "上月"
                        ).forEach { (c, label) ->
                            DropdownMenuItem(text = { Text(label) }, onClick = {
                                cycle = c; cycleExpanded = false
                            })
                        }
                    }
                }
                Box {
                    TextButton(onClick = { typeExpanded = true }) {
                        Text(typeLabel)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(expanded = typeExpanded, onDismissRequest = { typeExpanded = false }) {
                        listOf(TrafficType.MOBILE to "流量", TrafficType.WIFI to "WiFi").forEach { (t, label) ->
                            DropdownMenuItem(text = { Text(label) }, onClick = {
                                type = t; typeExpanded = false
                            })
                        }
                    }
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularWavyProgressIndicator(modifier = Modifier.size(48.dp))
                }
            } else if (data.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("暂无流量数据", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
                ) {
                    items(data, key = { "${it.uid}_${it.appName}" }) { item ->
                        TrafficItem(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun TrafficItem(item: AppTrafficInfo) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIconImage(icon = item.appIcon, name = item.appName)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.appName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column {
                        Text("下载", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(item.formatRx(), style = MaterialTheme.typography.bodySmall)
                    }
                    Column {
                        Text("上传", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(item.formatTx(), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Text(
                item.formatTotal(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
