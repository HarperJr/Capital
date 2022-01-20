package com.harper.capital.ui

import com.harper.capital.prefs.SharedPrefs
import com.harper.capital.ui.model.ColorTheme
import kotlinx.coroutines.flow.Flow

private const val COLOR_THEME_KEY = "color_theme_key"

class ColorThemeProvider(private val sharedPrefs: SharedPrefs) {
    var colorTheme: String by sharedPrefs.pref(
        key = COLOR_THEME_KEY,
        defaultValue = ColorTheme.SYSTEM.name
    )
    val colorThemeFlow: Flow<String>
        get() = sharedPrefs.prefFlow(COLOR_THEME_KEY, defaultValue = ColorTheme.SYSTEM.name)
}
