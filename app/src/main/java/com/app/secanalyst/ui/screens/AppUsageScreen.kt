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
import androidx.compose.runtime.DisposableEffect
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
import com.app.secanalyst.ui.compoment.AppIconImage
import com.app.secanalyst.utils.AppUsageInfo
import com.app.secanalyst.utils.AppUsageUtil
import com.app.secanalyst.utils.PermissionUtil
import com.app.secanalyst.utils.UsagePeriod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppUsageScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasPermission by remember { mutableStateOf(PermissionUtil.hasUsageStatsPermission(context)) }
    var showDialog by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf<List<AppUsageInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var period by remember { mutableStateOf(UsagePeriod.TODAY) }
    var expanded by remember { mutableStateOf(false) }

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
            text = { Text("应用使用情况需要\"使用情况访问\"权限，请在设置中找到本应用并启用。") },
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

    val periodLabel = when (period) {
        UsagePeriod.TODAY -> "今日"
        UsagePeriod.YESTERDAY -> "昨日"
        UsagePeriod.THIS_WEEK -> "本周"
        UsagePeriod.THIS_MONTH -> "本月"
    }

    fun load() {
        if (!hasPermission) return
        scope.launch {
            isLoading = true
            data = withContext(Dispatchers.IO) {
                AppUsageUtil.query(context, period)
            }
            isLoading = false
        }
    }

    LaunchedEffect(period, hasPermission) {
        if (hasPermission) load()
    }

    val totalItem = data.find { it.packageName == "TOTAL" }
    val appItems = data.filter { it.packageName != "TOTAL" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("应用使用情况") },
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text(periodLabel)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        listOf(
                            UsagePeriod.TODAY to "今日", UsagePeriod.YESTERDAY to "昨日",
                            UsagePeriod.THIS_WEEK to "本周", UsagePeriod.THIS_MONTH to "本月"
                        ).forEach { (p, label) ->
                            DropdownMenuItem(text = { Text(label) }, onClick = {
                                period = p; expanded = false
                            })
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (totalItem != null) {
                    Text(
                        "总计: ${totalItem.formatUsageTime()}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularWavyProgressIndicator(modifier = Modifier.size(48.dp))
                }
            } else if (appItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("暂无使用数据", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
                ) {
                    items(appItems, key = { it.packageName }) { item ->
                        UsageItem(item, totalItem?.usageTime ?: 1L)
                    }
                }
            }
        }
    }
}

@Composable
private fun UsageItem(item: AppUsageInfo, totalTime: Long) {
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
                Text(
                    "${item.formatUsageTime()} (${item.formatLastUsed()})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                "${String.format("%.1f", item.percentage(totalTime))}%",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
