package com.app.secanalyst.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.secanalyst.model.switch.HomeCardSwitch
import com.app.secanalyst.ui.toast.LocalToastState

@Composable
fun HomeCardSwitchDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var ip by remember { mutableStateOf(HomeCardSwitch.ip) }
    var basic by remember { mutableStateOf(HomeCardSwitch.basic) }
    var screen by remember { mutableStateOf(HomeCardSwitch.screen) }
    var network by remember { mutableStateOf(HomeCardSwitch.network) }
    var memory by remember { mutableStateOf(HomeCardSwitch.memory) }
    var storage by remember { mutableStateOf(HomeCardSwitch.storage) }
    var battery by remember { mutableStateOf(HomeCardSwitch.battery) }
    val toastState = LocalToastState.current

    fun tryOff(newVal: Boolean, set: (Boolean) -> Unit, others: List<Boolean>) {
        if (!newVal && others.none { it }) {
            toastState.show(
                icon = Icons.Default.Warning,
                message = "至少要保留一个"
            )
            return
        }
        set(newVal)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("首页卡片", color = MaterialTheme.colorScheme.onSurface) },
        text = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("IP 信息", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Switch(checked = ip, onCheckedChange = { tryOff(it, { ip = it }, listOf(basic, screen, network, memory, storage, battery)) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("基本信息", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Switch(checked = basic, onCheckedChange = { tryOff(it, { basic = it }, listOf(ip, screen, network, memory, storage, battery)) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("屏幕信息", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Switch(checked = screen, onCheckedChange = { tryOff(it, { screen = it }, listOf(ip, basic, network, memory, storage, battery)) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("网络信息", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Switch(checked = network, onCheckedChange = { tryOff(it, { network = it }, listOf(ip, basic, screen, memory, storage, battery)) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("内存信息", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Switch(checked = memory, onCheckedChange = { tryOff(it, { memory = it }, listOf(ip, basic, screen, network, storage, battery)) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("存储信息", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Switch(checked = storage, onCheckedChange = { tryOff(it, { storage = it }, listOf(ip, basic, screen, network, memory, battery)) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("电池信息", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Switch(checked = battery, onCheckedChange = { tryOff(it, { battery = it }, listOf(ip, basic, screen, network, memory, storage)) })
                }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                HomeCardSwitch.ip = ip
                HomeCardSwitch.basic = basic
                HomeCardSwitch.screen = screen
                HomeCardSwitch.network = network
                HomeCardSwitch.memory = memory
                HomeCardSwitch.storage = storage
                HomeCardSwitch.battery = battery
                onConfirm()
            }) {
                Text("确定", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}
