package com.app.secanalyst.ui.screens

import android.content.Intent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.secanalyst.IpDetailActivity
import com.app.secanalyst.model.PreviewModel
import com.app.secanalyst.model.switch.HomeCardSwitch
import com.app.secanalyst.ui.compoment.DeviceInfoCard
import com.app.secanalyst.ui.compoment.getDeviceInfoCardIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val ipInfo = PreviewModel.getIpInfoPreview()
    val previewCards = PreviewModel.getDeviceInfoPreviewCards().filter { HomeCardSwitch.isEnabled(it.id) }

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
            previewCards.forEach { preview ->
                DeviceInfoCard(
                    title = preview.title,
                    icon = getDeviceInfoCardIcon(preview.id),
                    items = preview.items,
                    onRefresh = {}
                )
            }
        }
    }
}
