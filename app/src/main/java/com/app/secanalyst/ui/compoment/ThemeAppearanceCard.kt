package com.app.secanalyst.ui.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.secanalyst.model.switch.ThemeAppearance
import com.app.secanalyst.model.switch.ThemeSwitch

private val PreviewLightBg = Color(0xFFF3EDF7)
private val PreviewLightBar = Color(0xFFE7E0EC)
private val PreviewLightNav = Color(0xFFE7E0EC)
private val PreviewLightBtn = Color(0xFF49454F)
private val PreviewDarkBg = Color(0xFF1C1B1F)
private val PreviewDarkBar = Color(0xFF2B2930)
private val PreviewDarkNav = Color(0xFF2B2930)
private val PreviewDarkBtn = Color(0xFFE6E1E5)

@Composable
private fun AppearancePreviewBox(
    modifier: Modifier = Modifier,
    bgColor: Color,
    barColor: Color,
    navColor: Color,
    buttonColor: Color
) {
    Column(modifier = modifier.background(bgColor)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(barColor, RoundedCornerShape(4.dp))
                .padding(vertical = 3.dp)
        )
        Box(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(navColor)
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { i ->
                Box(
                    modifier = Modifier
                        .size(if (i == 1) 7.dp else 5.dp)
                        .background(buttonColor, CircleShape)
                )
            }
        }
    }
}

@Composable
fun ThemeAppearanceCard(
    modifier: Modifier = Modifier,
    currentAppearance: ThemeAppearance = ThemeSwitch.appearance,
    onAppearanceSelected: (ThemeAppearance) -> Unit = { ThemeSwitch.appearance = it },
    useAmoled: Boolean = ThemeSwitch.useAmoled,
    onAmoledChange: (Boolean) -> Unit = { ThemeSwitch.useAmoled = it }
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "设置",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf(
                    ThemeAppearance.LIGHT to "浅色",
                    ThemeAppearance.DARK to "深色",
                    ThemeAppearance.FOLLOW_SYSTEM to "跟随系统"
                ).forEach { (appearance, label) ->
                    val selected = appearance == currentAppearance
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAppearanceSelected(appearance) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .then(
                                    if (selected) Modifier.border(
                                        2.dp,
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(12.dp)
                                    ) else Modifier
                                )
                        ) {
                            when (appearance) {
                                ThemeAppearance.LIGHT -> AppearancePreviewBox(
                                    modifier = Modifier.matchParentSize(),
                                    bgColor = PreviewLightBg,
                                    barColor = PreviewLightBar,
                                    navColor = PreviewLightNav,
                                    buttonColor = PreviewLightBtn
                                )
                                ThemeAppearance.DARK -> AppearancePreviewBox(
                                    modifier = Modifier.matchParentSize(),
                                    bgColor = PreviewDarkBg,
                                    barColor = PreviewDarkBar,
                                    navColor = PreviewDarkNav,
                                    buttonColor = PreviewDarkBtn
                                )
                                ThemeAppearance.FOLLOW_SYSTEM -> AppearancePreviewBox(
                                    modifier = Modifier.matchParentSize(),
                                    bgColor = MaterialTheme.colorScheme.surface,
                                    barColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    navColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    buttonColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "背景改为纯黑（暗色模式生效）",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = useAmoled,
                    onCheckedChange = onAmoledChange
                )
            }
        }
    }
}
