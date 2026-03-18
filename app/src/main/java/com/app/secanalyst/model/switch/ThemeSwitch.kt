package com.app.secanalyst.model.switch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.app.secanalyst.ui.theme.SecAnalystPalette
import com.app.secanalyst.utils.SharedPreferenceUtil

enum class ThemeAppearance {
    LIGHT,
    DARK,
    FOLLOW_SYSTEM
}

enum class ThemeMode {
    PRESET,
    ALGORITHM,
    DYNAMIC
}

object ThemeSwitch {
    private var _appearance by mutableStateOf(ThemeAppearance.FOLLOW_SYSTEM)
    var appearance
        get() = _appearance
        set(value) {
            _appearance = value
            if (SharedPreferenceUtil.isInitialized()) SharedPreferenceUtil.saveAppearance(value)
        }

    private var _themeMode by mutableStateOf(ThemeMode.DYNAMIC)
    var themeMode
        get() = _themeMode
        set(value) {
            _themeMode = value
            if (SharedPreferenceUtil.isInitialized()) SharedPreferenceUtil.saveThemeMode(value)
        }

    private var _useAmoled by mutableStateOf(false)
    var useAmoled
        get() = _useAmoled
        set(value) {
            _useAmoled = value
            if (SharedPreferenceUtil.isInitialized()) SharedPreferenceUtil.saveUseAmoled(value)
        }

    private var _palette by mutableStateOf(SecAnalystPalette.GREEN)
    var palette
        get() = _palette
        set(value) {
            _palette = value
            if (SharedPreferenceUtil.isInitialized()) SharedPreferenceUtil.savePalette(value)
        }

    fun initFromPreference() {
        _appearance = SharedPreferenceUtil.getAppearance()
        _themeMode = SharedPreferenceUtil.getThemeMode()
        _useAmoled = SharedPreferenceUtil.getUseAmoled()
        _palette = SharedPreferenceUtil.getPalette()
    }
}
