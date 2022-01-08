package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun Chip(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, text: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .background(CapitalColors.Blue, shape = RoundedCornerShape(percent = 50))
            .defaultMinSize(minHeight = 26.dp)
            .padding(horizontal = 16.dp)
            .clickable { onClick?.invoke() }
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            text.invoke()
        }
    }
}

@Preview
@Composable
private fun ChipLight() {
    ComposablePreview {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            Chip {
                Text(
                    text = "I am a chip",
                    color = CapitalColors.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChipDark() {
    ComposablePreview(isDark = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            Chip {
                Text(
                    text = "I am a chip",
                    style = CapitalTheme.typography.regular,
                    color = CapitalColors.White
                )
            }
        }
    }
}
