package com.app.secanalyst.ui.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.app.secanalyst.utils.DataPlanUtil
import com.app.secanalyst.utils.PermissionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DataPlanScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasPermission by remember { mutableStateOf(PermissionUtil.hasUsageStatsPermission(context)) }
    var showPermDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var mobileData by remember { mutableLongStateOf(0L) }
    var wifiData by remember { mutableLongStateOf(0L) }
    var showPlanDialog by remember { mutableStateOf(false) }

    val prefs = remember {
        context.getSharedPreferences("data_plan", android.content.Context.MODE_PRIVATE)
    }
    var planGB by remember { mutableFloatStateOf(prefs.getFloat("plan_gb", 10f)) }

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
            showPermDialog = true
            isLoading = false
        }
    }

    if (showPermDialog && !hasPermission) {
        AlertDialog(
            onDismissRequest = { showPermDialog = false; onBack() },
            title = { Text("需要权限") },
            text = { Text("流量套餐需要\"使用情况访问\"权限，请在设置中找到本应用并启用。") },
            confirmButton = {
                TextButton(onClick = {
                    showPermDialog = false
                    context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                }) { Text("去授权") }
            },
            dismissButton = {
                TextButton(onClick = { showPermDialog = false; onBack() }) { Text("取消") }
            }
        )
    }

    fun load() {
        if (!hasPermission) return
        scope.launch {
            isLoading = true
            val (mobile, wifi) = withContext(Dispatchers.IO) {
                Pair(DataPlanUtil.getMonthlyMobileData(context), DataPlanUtil.getMonthlyWifiData(context))
            }
            mobileData = mobile
            wifiData = wifi
            isLoading = false
        }
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) load()
    }

    if (showPlanDialog) {
        var inputText by remember { mutableStateOf(planGB.toString()) }
        AlertDialog(
            onDismissRequest = { showPlanDialog = false },
            title = { Text("设置流量套餐") },
            text = {
                Column {
                    Text("请输入流量套餐(GB):")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val v = inputText.toFloatOrNull()
                    if (v != null && v > 0) {
                        planGB = v
                        prefs.edit().putFloat("plan_gb", v).apply()
                    }
                    showPlanDialog = false
                }) { Text("确定") }
            },
            dismissButton = {
                TextButton(onClick = { showPlanDialog = false }) { Text("取消") }
            }
        )
    }

    val usedGB = mobileData / (1024f * 1024f * 1024f)
    val remainingGB = planGB - usedGB
    val percentage = if (planGB > 0) (remainingGB / planGB).coerceIn(0f, 1f) else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("流量套餐") },
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
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularWavyProgressIndicator(modifier = Modifier.size(48.dp))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("移动数据", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(DataPlanUtil.formatBytes(mobileData), fontWeight = FontWeight.Bold)
                        }
                    }
                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("WiFi数据", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(DataPlanUtil.formatBytes(wifiData), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                val arcColor = if (remainingGB >= 0) MaterialTheme.colorScheme.primary else Color.Red
                val bgColor = MaterialTheme.colorScheme.surfaceVariant

                Box(
                    modifier = Modifier.size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawArc(
                            color = bgColor,
                            startAngle = 180f,
                            sweepAngle = 180f,
                            useCenter = false,
                            style = Stroke(width = 20f)
                        )
                        drawArc(
                            color = arcColor,
                            startAngle = 180f,
                            sweepAngle = 180f * percentage,
                            useCenter = false,
                            style = Stroke(width = 20f)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "%.1f%%".format(percentage * 100),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = arcColor
                        )
                        Text(
                            "剩余: %.2fGB / %.2fGB".format(remainingGB.coerceAtLeast(0f), planGB),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = { showPlanDialog = true }) {
                    Text("设置流量套餐")
                }
            }
        }
    }
}
