package com.harper.core.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.harper.core.theme.CapitalTheme

@Composable
fun CPreview(
    isDark: Boolean? = null,
    content: @Composable () -> Unit
) {
    CapitalTheme(isDark ?: isSystemInDarkTheme()) {
        Surface(color = CapitalTheme.colors.background) {
            content.invoke()
        }
    }
}
