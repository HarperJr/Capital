package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun CChip(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        color = CapitalTheme.colors.secondary,
        shape = CircleShape,
        onClick = { onClick?.invoke() }
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            content.invoke()
        }
    }
}

@Preview
@Composable
private fun CChipLight() {
    CPreview {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            CChip {
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
private fun CChipDark() {
    CPreview(isDark = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            CChip {
                Text(
                    text = "I am a chip",
                    style = CapitalTheme.typography.regular,
                    color = CapitalColors.White
                )
            }
        }
    }
}
