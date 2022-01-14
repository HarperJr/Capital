package com.harper.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalButtonColors
import com.harper.core.theme.capitalButtonElevation

private val defaultButtonColors: ButtonColors
    @Composable
    get() = capitalButtonColors()

private val borderlessButtonColors: ButtonColors
    @Composable
    get() = capitalButtonColors(
        backgroundColor = CapitalColors.Transparent,
        disabledBackgroundColor = CapitalColors.Transparent
    )

@Composable
fun CapitalButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = CapitalColors.White,
    enabled: Boolean = true,
    borderless: Boolean = false,
    buttonColors: ButtonColors = defaultButtonColors,
    border: BorderStroke? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(42.dp),
        shape = CapitalTheme.shapes.medium,
        colors = if (borderless) borderlessButtonColors else buttonColors,
        elevation = capitalButtonElevation(),
        enabled = enabled,
        border = border,
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (leadingIcon != null) {
                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    leadingIcon.invoke()
                }
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = CapitalTheme.typography.button,
                color = if (borderless) CapitalColors.Blue else textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionButtonLight() {
    ComposablePreview {
        Column {
            CapitalButton(modifier = Modifier.padding(16.dp), text = "Push me") {}
            CapitalButton(modifier = Modifier.padding(16.dp), borderless = true, text = "Push me") {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionButtonDark() {
    ComposablePreview(isDark = true) {
        Column {
            CapitalButton(modifier = Modifier.padding(16.dp), text = "Push me") {}
            CapitalButton(modifier = Modifier.padding(16.dp), borderless = true, text = "Push me") {}
        }
    }
}