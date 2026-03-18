package com.app.secanalyst.ui.compoment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.secanalyst.model.PreviewModel
import com.app.secanalyst.model.switch.ThemeSwitch
import com.app.secanalyst.ui.theme.SecAnalystPalette
import com.app.secanalyst.ui.theme.SecAnalystTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ThemeColorCard(
    modifier: Modifier = Modifier,
    selectedPalette: SecAnalystPalette = ThemeSwitch.palette,
    onPaletteSelected: (SecAnalystPalette) -> Unit = { ThemeSwitch.palette = it }
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "主题色",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            LazyRow(
                modifier = Modifier.padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = SecAnalystPalette.entries,
                    key = { it }
                ) { palette ->
                    ThemeColorItem(
                        palette = palette,
                        selected = palette == selectedPalette,
                        onClick = { onPaletteSelected(palette) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ThemeColorCardPreview() {
    SecAnalystTheme {
        ThemeColorCard(selectedPalette = PreviewModel.themeColorPreviewPalette)
    }
}
