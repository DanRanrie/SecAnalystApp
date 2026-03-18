package com.app.secanalyst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.app.secanalyst.model.switch.ThemeAppearance
import com.app.secanalyst.model.switch.ThemeSwitch
import com.app.secanalyst.ui.screens.IpDetailScreen
import com.app.secanalyst.ui.theme.SecAnalystTheme

class IpDetailActivity : ComponentActivity() {
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
                IpDetailScreen(
                    onBack = { 
                        // 使用系统的返回分发器，这是开启预测性返回动画的关键
                        onBackPressedDispatcher.onBackPressed() 
                    }
                )
            }
        }
    }
}
