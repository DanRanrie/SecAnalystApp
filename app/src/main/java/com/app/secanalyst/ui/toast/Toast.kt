package com.app.secanalyst.ui.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

data class ToastParams(
    val icon: ImageVector,
    val iconColor: androidx.compose.ui.graphics.Color?,
    val message: String,
    val textColor: androidx.compose.ui.graphics.Color?,
    val durationMs: Long,
    val backgroundColor: androidx.compose.ui.graphics.Color?,
    val backgroundColorStatic: androidx.compose.ui.graphics.Color?
)

class ToastState {
    var current by mutableStateOf<ToastParams?>(null)
        private set

    fun show(
        icon: ImageVector,
        message: String,
        iconColor: androidx.compose.ui.graphics.Color? = null,
        textColor: androidx.compose.ui.graphics.Color? = null,
        durationMs: Long = 1500L,
        backgroundColor: androidx.compose.ui.graphics.Color? = null,
        backgroundColorStatic: androidx.compose.ui.graphics.Color? = null
    ) {
        current = ToastParams(
            icon = icon,
            iconColor = iconColor,
            message = message,
            textColor = textColor,
            durationMs = durationMs,
            backgroundColor = backgroundColor,
            backgroundColorStatic = backgroundColorStatic
        )
    }

    fun dismiss() {
        current = null
    }
}

@Composable
fun ToastUI(
    state: ToastState,
    modifier: Modifier = Modifier
) {
    val params = state.current ?: return
    var visible by remember(params) { mutableStateOf(false) }

    LaunchedEffect(params) {
        visible = true
        delay(params.durationMs)
        visible = false
        delay(300)
        state.dismiss()
    }

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it }
    ) {
        val theme = MaterialTheme.colorScheme
        val bgColor = params.backgroundColor
            ?: params.backgroundColorStatic
            ?: theme.surfaceContainerHigh
        val textColor = params.textColor ?: theme.onSurface
        val iconColor = params.iconColor ?: theme.onSurface
        Surface(
            modifier = Modifier
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .widthIn(max = 520.dp)
                .fillMaxWidth(),
            color = bgColor,
            shape = RoundedCornerShape(26.dp),
            tonalElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = params.icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconColor
                )
                Text(
                    text = params.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
        }
    }
}
