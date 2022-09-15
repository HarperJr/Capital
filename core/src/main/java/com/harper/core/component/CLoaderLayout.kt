package com.harper.core.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.harper.core.theme.CapitalTheme

@Composable
fun CLoaderLayout(
    isLoading: Boolean,
    loaderContent: @Composable () -> Unit = { CDefaultLoader() },
    content: @Composable () -> Unit
) {
    if (isLoading) {
        loaderContent.invoke()
    } else {
        content.invoke()
    }
}

@Composable
private fun CDefaultLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CapitalTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = CapitalTheme.colors.secondary)
    }
}
