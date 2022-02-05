package com.harper.core.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.harper.core.theme.CapitalTheme

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun CLoaderLayout(
    isLoading: Boolean,
    loaderContent: @Composable () -> Unit = { CDefaultLoader() },
    content: @Composable () -> Unit
) {
    AnimatedContent(
        targetState = isLoading,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(900, delayMillis = 90)
            ) with fadeOut(
                animationSpec = tween(90)
            )
        }
    ) { isVisible ->
        if (isVisible) {
            loaderContent.invoke()
        } else {
            content.invoke()
        }
    }
}

@Composable
private fun CDefaultLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = CapitalTheme.colors.secondary)
    }
}
