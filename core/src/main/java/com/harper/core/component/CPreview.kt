package com.harper.core.component

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.harper.core.theme.CapitalTheme

@Composable
fun CPreview(
    isDark: Boolean = false,
    content: @Composable () -> Unit
) {
    CapitalTheme(isDark) {
        Surface(color = CapitalTheme.colors.background) {
            content.invoke()
        }
    }
}
