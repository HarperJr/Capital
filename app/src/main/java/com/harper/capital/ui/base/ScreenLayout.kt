package com.harper.capital.ui.base

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.harper.capital.ui.ColorThemeProvider
import com.harper.capital.ui.model.ColorTheme
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import org.koin.androidx.compose.get

@Composable
fun ScreenLayout(content: @Composable () -> Unit) {
    val themeProvider: ColorThemeProvider = get()
    val colorTheme by themeProvider.colorThemeFlow.collectAsState(initial = ColorTheme.SYSTEM.name)
    val isDark = when (ColorTheme.valueOf(colorTheme)) {
        ColorTheme.LIGHT -> false
        ColorTheme.DARK -> true
        ColorTheme.SYSTEM -> isSystemInDarkTheme()
    }

    CapitalTheme(isDark = isDark) {
        val useDarkIcons = CapitalTheme.colors.isLight
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = CapitalColors.Transparent,
                darkIcons = useDarkIcons
            )
        }
        ProvideWindowInsets(windowInsetsAnimationsEnabled = false) {
            Surface(
                color = CapitalTheme.colors.background,
                contentColor = LocalContentColor.current
            ) {
                content.invoke()
            }
        }
    }
}
