package com.app.secanalyst.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.secanalyst.utils.DiagnoseResult
import com.app.secanalyst.utils.NetworkDiagnoseUtil
import com.app.secanalyst.utils.PacketLossResult
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NetworkDiagnosisScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var result by remember { mutableStateOf<DiagnoseResult?>(null) }
    var packetLoss by remember { mutableStateOf<PacketLossResult?>(null) }
    var isDiagnosing by remember { mutableStateOf(false) }
    var isPacketTesting by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("网络诊断") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isDiagnosing = true
                        result = null
                        packetLoss = null
                        result = NetworkDiagnoseUtil.diagnose(context)
                        isDiagnosing = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isDiagnosing && !isPacketTesting
            ) { Text("开始诊断") }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        isPacketTesting = true
                        packetLoss = NetworkDiagnoseUtil.packetLossTest()
                        isPacketTesting = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = result != null && !isDiagnosing && !isPacketTesting
            ) { Text("发包测试") }

            Spacer(modifier = Modifier.height(16.dp))

            if (isDiagnosing || isPacketTesting) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularWavyProgressIndicator(modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            if (isDiagnosing) "诊断中，涉及测速等操作，预计30秒..."
                            else "发包测试中，预计65秒...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            result?.let { info ->
                DiagnoseCard("基本信息") {
                    DiagnoseRow("WiFi名称", info.wifiName)
                    DiagnoseRow("连接类型", info.connectionType)
                    DiagnoseRow("信号强度", info.signalStrength)
                    DiagnoseRow("连通性", info.connectivity)
                    DiagnoseRow("下载速度", info.downloadSpeed)
                    DiagnoseRow("上传速度", info.uploadSpeed)
                    DiagnoseRow("时延", info.latency)
                    DiagnoseRow("稳定性", info.stability)
                    DiagnoseRow("抖动", info.jitter)
                }

                packetLoss?.let { pl ->
                    DiagnoseCard("发包测试") {
                        DiagnoseRow("平均丢包率", pl.avgLoss)
                        pl.details.forEach { (label, value) ->
                            DiagnoseRow(label, value)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DiagnoseCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun DiagnoseRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        Text(value, fontWeight = FontWeight.Medium)
    }
}
