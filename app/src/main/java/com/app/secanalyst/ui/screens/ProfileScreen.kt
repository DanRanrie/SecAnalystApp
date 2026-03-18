package com.app.secanalyst.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.secanalyst.ThemeColorActivity
import com.app.secanalyst.ui.compoment.SettingsCard
import com.app.secanalyst.ui.compoment.SettingsSectionTitle
import com.app.secanalyst.ui.dialog.HomeCardSwitchDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    var notificationEnabled by remember { mutableStateOf(true) }
    var showHomeCardDialog by remember { mutableStateOf(false) }

    if (showHomeCardDialog) {
        HomeCardSwitchDialog(
            onDismiss = { showHomeCardDialog = false },
            onConfirm = { showHomeCardDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("我的") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SettingsSectionTitle(text = "主题")
            SettingsCard(
                icon = Icons.Default.Palette,
                title = "主题与颜色",
                subtitle = "主题色、浅深色",
                onClick = {
                    context.startActivity(android.content.Intent(context, ThemeColorActivity::class.java))
                },
                trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            SettingsSectionTitle(text = "个性设置")
            SettingsCard(
                icon = Icons.Default.Dashboard,
                title = "首页卡片",
                subtitle = "显示或隐藏首页各信息卡片",
                onClick = { showHomeCardDialog = true },
                trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) }
            )
            SettingsCard(
                icon = Icons.Default.Notifications,
                title = "通知",
                subtitle = "推送与提醒",
                onClick = null,
                trailing = {
                    Switch(
                        checked = notificationEnabled,
                        onCheckedChange = { notificationEnabled = it }
                    )
                }
            )
            SettingsCard(
                icon = Icons.Default.Settings,
                title = "更多设置",
                subtitle = "数据、缓存与关于",
                onClick = { },
                trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
