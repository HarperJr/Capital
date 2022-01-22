package com.harper.core.theme

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun capitalButtonColors(
    backgroundColor: Color = CapitalTheme.colors.secondary,
    disabledBackgroundColor: Color = CapitalTheme.colors.secondaryVariant,
    contentColor: Color = CapitalTheme.colors.onSecondary,
    disabledContentColor: Color = CapitalTheme.colors.onSecondary
): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    disabledBackgroundColor = disabledBackgroundColor,
    disabledContentColor = disabledContentColor
)
