package com.harper.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalButtonColors
import com.harper.core.theme.capitalButtonElevation

private val minButtonHeight = 42.dp

private val defaultButtonColors: ButtonColors
    @Composable
    get() = capitalButtonColors()

private val borderlessButtonColors: ButtonColors
    @Composable
    get() = capitalButtonColors(
        backgroundColor = CapitalColors.Transparent,
        disabledBackgroundColor = CapitalColors.Transparent,
        contentColor = CapitalTheme.colors.secondary,
        disabledContentColor = CapitalTheme.colors.textSecondary
    )

@Composable
fun CButton(
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
        modifier = modifier.height(minButtonHeight),
        shape = CapitalTheme.shapes.medium,
        colors = if (borderless) borderlessButtonColors else buttonColors,
        elevation = capitalButtonElevation(),
        enabled = enabled,
        border = border,
        onClick = onClick
    ) {
        if (leadingIcon != null) {
            Box(
                modifier = Modifier
                    .padding(end = ButtonDefaults.IconSpacing)
                    .size(ButtonDefaults.IconSize)
            ) {
                leadingIcon.invoke()
            }
        }
        Text(
            text = text,
            style = CapitalTheme.typography.button,
            color = if (borderless) LocalContentColor.current else textColor,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CButtonLight() {
    CPreview {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CButton(modifier = Modifier.padding(16.dp), text = "Push me") {}
            CButton(modifier = Modifier.padding(16.dp), enabled = false, text = "Push me") {}
            CButton(modifier = Modifier.padding(16.dp), borderless = true, text = "Push me") {}
            CButton(
                modifier = Modifier.padding(16.dp),
                enabled = false,
                borderless = true,
                text = "Push me"
            ) {}
            CButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = false,
                text = "Push me",
                leadingIcon = {
                    Icon(
                        imageVector = CapitalIcons.Add,
                        contentDescription = null
                    )
                }
            ) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CButtonDark() {
    CPreview(isDark = true) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CButton(modifier = Modifier.padding(16.dp), text = "Push me") {}
            CButton(modifier = Modifier.padding(16.dp), enabled = false, text = "Push me") {}
            CButton(modifier = Modifier.padding(16.dp), borderless = true, text = "Push me") {}
            CButton(
                modifier = Modifier.padding(16.dp),
                enabled = false,
                borderless = true,
                text = "Push me"
            ) {}
            CButton(
                modifier = Modifier.padding(16.dp),
                enabled = false,
                text = "Push me, i have a long text",
                leadingIcon = {
                    Icon(
                        imageVector = CapitalIcons.Add,
                        contentDescription = null
                    )
                }
            ) {}
        }
    }
}
