package com.app.secanalyst.ui.compoment

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.secanalyst.model.PreviewModel
import com.app.secanalyst.ui.theme.PaletteScheme
import com.app.secanalyst.ui.theme.SecAnalystPalette
import com.app.secanalyst.ui.theme.SecAnalystTheme

fun SecAnalystPalette.paletteLabel(): String = when (this) {
        SecAnalystPalette.RED -> "朱砂"
        SecAnalystPalette.ORANGE -> "琥珀"
        SecAnalystPalette.YELLOW -> "秋香"
        SecAnalystPalette.GREEN -> "翠微"
        SecAnalystPalette.CYAN -> "天青"
        SecAnalystPalette.BLUE -> "靛蓝"
        SecAnalystPalette.PURPLE -> "紫棠"
        SecAnalystPalette.NEUTRAL -> "苍灰"
        SecAnalystPalette.BROWN -> "赭石"
}

@Composable
fun ThemeColorItem(
    palette: SecAnalystPalette,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = PaletteScheme.seedColor(palette)
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val trimEnd by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(durationMillis = 280),
        label = "checkTrim"
    )
    val isDark = isSystemInDarkTheme()
    val checkColor = if (isDark) Color.Black else Color.White
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier.size(44.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(color)
            )
            if (selected) {
                val pathMeasure = remember { PathMeasure() }
                val segmentPath = remember { Path() }
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height
                    val strokeWidth = 2.5.dp.toPx()
                    val path = Path().apply {
                        moveTo(w * 0.22f, h * 0.52f)
                        lineTo(w * 0.42f, h * 0.72f)
                        lineTo(w * 0.78f, h * 0.28f)
                    }
                    pathMeasure.setPath(path, false)
                    segmentPath.reset()
                    pathMeasure.getSegment(0f, trimEnd * pathMeasure.length, segmentPath, startWithMoveTo = true)
                    drawPath(
                        segmentPath,
                        checkColor,
                        style = Stroke(width = strokeWidth)
                    )
                }
            }
        }
        Text(
            text = palette.paletteLabel(),
            style = MaterialTheme.typography.labelSmall,
            color = onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun ThemeColorItemPreview() {
    SecAnalystTheme {
        ThemeColorItem(
            palette = PreviewModel.themeColorPreviewPalette,
            selected = true,
            onClick = {}
        )
    }
}
