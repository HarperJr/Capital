package com.harper.capital.shelter.core

import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun ScreenLayout(content: @Composable () -> Unit) {
    CapitalTheme(isDark = false) {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = CapitalTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = CapitalColors.Transparent,
                darkIcons = useDarkIcons
            )
        }

        Surface(
            color = CapitalTheme.colors.background,
            contentColor = LocalContentColor.current
        ) {
            content.invoke()
        }
    }
}
