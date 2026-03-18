package com.app.secanalyst.utils

import android.content.Context
import android.content.SharedPreferences
import com.app.secanalyst.model.switch.ThemeAppearance
import com.app.secanalyst.model.switch.ThemeMode
import com.app.secanalyst.ui.theme.SecAnalystPalette

object SharedPreferenceUtil {

    private const val PREF_NAME = "sec_analyst_prefs"
    private const val KEY_APPEARANCE = "key_appearance"
    private const val KEY_THEME_MODE = "key_theme_mode"
    private const val KEY_USE_AMOLED = "key_use_amoled"
    private const val KEY_PALETTE = "key_palette"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        if (!::prefs.isInitialized) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun isInitialized(): Boolean = ::prefs.isInitialized

    fun getAppearance(): ThemeAppearance {
        val name = prefs.getString(KEY_APPEARANCE, ThemeAppearance.FOLLOW_SYSTEM.name) ?: ThemeAppearance.FOLLOW_SYSTEM.name
        return runCatching { ThemeAppearance.valueOf(name) }.getOrDefault(ThemeAppearance.FOLLOW_SYSTEM)
    }

    fun getThemeMode(): ThemeMode {
        val name = prefs.getString(KEY_THEME_MODE, ThemeMode.DYNAMIC.name) ?: ThemeMode.DYNAMIC.name
        return runCatching { ThemeMode.valueOf(name) }.getOrDefault(ThemeMode.DYNAMIC)
    }

    fun getUseAmoled(): Boolean {
        return prefs.getBoolean(KEY_USE_AMOLED, false)
    }

    fun getPalette(): SecAnalystPalette {
        val name = prefs.getString(KEY_PALETTE, SecAnalystPalette.GREEN.name) ?: SecAnalystPalette.GREEN.name
        return runCatching { SecAnalystPalette.valueOf(name) }.getOrDefault(SecAnalystPalette.GREEN)
    }

    fun saveAppearance(value: ThemeAppearance) {
        prefs.edit().setAppearance(value).apply()
    }

    fun saveThemeMode(value: ThemeMode) {
        prefs.edit().setMode(value).apply()
    }

    fun saveUseAmoled(value: Boolean) {
        prefs.edit().setUseAmoled(value).apply()
    }

    fun savePalette(value: SecAnalystPalette) {
        prefs.edit().setPalette(value).apply()
    }

    private fun SharedPreferences.Editor.setAppearance(value: ThemeAppearance): SharedPreferences.Editor {
        return putString(KEY_APPEARANCE, value.name)
    }

    private fun SharedPreferences.Editor.setMode(value: ThemeMode): SharedPreferences.Editor {
        return putString(KEY_THEME_MODE, value.name)
    }

    private fun SharedPreferences.Editor.setUseAmoled(value: Boolean): SharedPreferences.Editor {
        return putBoolean(KEY_USE_AMOLED, value)
    }

    private fun SharedPreferences.Editor.setPalette(value: SecAnalystPalette): SharedPreferences.Editor {
        return putString(KEY_PALETTE, value.name)
    }
}

