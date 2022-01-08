package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.harper.core.theme.CapitalTheme

@Composable
fun ComposablePreview(
    isDark: Boolean = false,
    content: @Composable () -> Unit
) {
    CapitalTheme(isDark) {
        Surface(color = CapitalTheme.colors.background) {
            content.invoke()
        }
    }
}
