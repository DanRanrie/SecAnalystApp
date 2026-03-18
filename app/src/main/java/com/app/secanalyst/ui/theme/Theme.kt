package com.app.secanalyst.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.app.secanalyst.model.switch.ThemeMode
import com.app.secanalyst.model.switch.ThemeSwitch
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamicColorScheme

@Composable
fun SecAnalystTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val themeMode = ThemeSwitch.themeMode
    val useAmoled = ThemeSwitch.useAmoled
    val palette = ThemeSwitch.palette

    val colorScheme = when {
        themeMode == ThemeMode.DYNAMIC && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        themeMode == ThemeMode.ALGORITHM -> {
            dynamicColorScheme(
                primary = PaletteScheme.seedColor(palette),
                isDark = darkTheme,
                isAmoled = useAmoled,
                style = PaletteStyle.TonalSpot
            )
        }
        else -> {
            val scheme = PaletteScheme.from(palette)
            var base = if (darkTheme) scheme.dark else scheme.light
            if (darkTheme && useAmoled) {
                base = base.copy(
                    background = Color.Black,
                    surface = Color.Black,
                    surfaceVariant = Color(0xFF1C1C1C),
                    surfaceContainer = Color(0xFF121212),
                    surfaceContainerHigh = Color(0xFF1F1F1F)
                )
            }
            base
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}