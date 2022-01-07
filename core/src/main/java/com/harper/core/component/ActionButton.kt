package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.*

@Composable
fun ActionButton(modifier: Modifier = Modifier, text: String, enabled: Boolean = true, borderless: Boolean = false, onClick: () -> Unit) {
    val buttonColors = if (borderless) capitalButtonBorderlessColors() else capitalButtonColors()
    val buttonElevation = if (borderless) capitalButtonBorderlessElevation() else capitalButtonElevation()
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = CapitalTheme.shapes.medium,
        elevation = buttonElevation,
        colors = buttonColors
    ) {
        val textColor = remember {
            if (borderless) CapitalColors.Blue else CapitalColors.White
        }
        Text(text = text, style = CapitalTheme.typography.regular, color = textColor)
    }
}

@Preview
@Composable
private fun ActionButtonLight() {
    ComposablePreview {
        Column(modifier = Modifier.background(CapitalTheme.colors.background)) {
            ActionButton(modifier = Modifier.padding(16.dp), text = "Push me") {}
            ActionButton(modifier = Modifier.padding(16.dp), borderless = true, text = "Push me") {}
        }
    }
}

@Preview
@Composable
private fun ActionButtonDark() {
    ComposablePreview(isDark = true) {
        Column(modifier = Modifier.background(CapitalTheme.colors.background)) {
            ActionButton(modifier = Modifier.padding(16.dp), text = "Push me") {}
            ActionButton(modifier = Modifier.padding(16.dp), borderless = true, text = "Push me") {}
        }
    }
}
