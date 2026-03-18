package com.app.secanalyst.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.app.secanalyst.model.switch.ThemeAppearance
import com.app.secanalyst.model.switch.ThemeSwitch
import com.app.secanalyst.ui.screens.AppUsageScreen
import com.app.secanalyst.ui.theme.SecAnalystTheme

class AppUsageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme = when (ThemeSwitch.appearance) {
                ThemeAppearance.LIGHT -> false
                ThemeAppearance.DARK -> true
                ThemeAppearance.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            }
            SecAnalystTheme(darkTheme = darkTheme) {
                AppUsageScreen(onBack = { onBackPressedDispatcher.onBackPressed() })
            }
        }
    }
}
