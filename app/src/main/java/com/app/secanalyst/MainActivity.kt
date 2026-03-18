package com.app.secanalyst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.app.secanalyst.model.switch.ThemeAppearance
import com.app.secanalyst.model.switch.ThemeSwitch
import com.app.secanalyst.ui.SecAnalystApp
import com.app.secanalyst.ui.theme.SecAnalystTheme
import com.app.secanalyst.utils.SharedPreferenceUtil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceUtil.init(applicationContext)
        ThemeSwitch.initFromPreference()
        enableEdgeToEdge()
        setContent {
            val darkTheme = when (ThemeSwitch.appearance) {
                ThemeAppearance.LIGHT -> false
                ThemeAppearance.DARK -> true
                ThemeAppearance.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            }
            SecAnalystTheme(darkTheme = darkTheme) {
                SecAnalystApp()
            }
        }
    }
}
