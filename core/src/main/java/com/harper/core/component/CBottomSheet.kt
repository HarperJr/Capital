package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.harper.core.theme.CapitalTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CBottomSheet(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = CapitalTheme.dimensions.medium),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(width = CapitalTheme.dimensions.largest, height = CapitalTheme.dimensions.small)
                    .background(color = CapitalTheme.colors.primaryVariant, shape = CircleShape)
            )
        }
        content.invoke()

        val bottomSpacerHeight = with(LocalDensity.current) {
            if (WindowInsets.isImeVisible) {
                WindowInsets.ime.getBottom(this).toDp()
            } else {
                WindowInsets.navigationBars.getBottom(this).toDp()
            }
        }
        Spacer(
            modifier = Modifier
                .height(bottomSpacerHeight)
                .fillMaxWidth()
        )
    }
}
