package com.app.secanalyst.ui.compoment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.secanalyst.model.PreviewModel
import com.app.secanalyst.model.switch.ThemeMode
import com.app.secanalyst.model.switch.ThemeSwitch
import com.app.secanalyst.ui.theme.SecAnalystTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ThemeModeCard(
    modifier: Modifier = Modifier,
    currentMode: ThemeMode = ThemeSwitch.themeMode,
    onModeSelected: (ThemeMode) -> Unit = { ThemeSwitch.themeMode = it }
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "主题模式",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ThemeMode.entries.forEach { mode ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        RadioButton(
                            selected = mode == currentMode,
                            onClick = { onModeSelected(mode) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = when (mode) {
                                ThemeMode.PRESET -> "预设"
                                ThemeMode.ALGORITHM -> "智能"
                                ThemeMode.DYNAMIC -> "动态"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ThemeModeCardPreview() {
    SecAnalystTheme {
        ThemeModeCard(currentMode = PreviewModel.themeModePreview)
    }
}
