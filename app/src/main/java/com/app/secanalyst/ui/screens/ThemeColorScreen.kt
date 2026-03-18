package com.app.secanalyst.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.secanalyst.ui.compoment.SettingsSectionTitle
import com.app.secanalyst.ui.compoment.ThemeAppearanceCard
import com.app.secanalyst.ui.compoment.ThemeColorCard
import com.app.secanalyst.ui.compoment.ThemeModeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeColorScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("主题与颜色") },
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
            ThemeAppearanceCard(modifier = Modifier.fillMaxWidth())
            SettingsSectionTitle(text = "主题", modifier = Modifier.padding(top = 20.dp))
            ThemeModeCard(modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
            ThemeColorCard(modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        }
    }
}
