package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun Chip(modifier: Modifier = Modifier, text: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .background(CapitalColors.Blue, shape = RoundedCornerShape(percent = 50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        text.invoke()
    }
}

@Preview
@Composable
private fun ChipLight() {
    ComposablePreview {
        Box(modifier = Modifier
            .background(CapitalTheme.colors.background)
            .padding(16.dp)) {
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

@Preview
@Composable
private fun ChipDark() {
    ComposablePreview(isDark = true) {
        Box(modifier = Modifier
            .background(CapitalTheme.colors.background)
            .padding(16.dp)) {
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
