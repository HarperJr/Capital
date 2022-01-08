package com.harper.core.theme

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun capitalButtonColors(
    backgroundColor: Color = CapitalColors.Blue,
    disabledBackgroundColor: Color = CapitalColors.BlueLight,
    contentColor: Color = CapitalColors.White
): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    disabledBackgroundColor = disabledBackgroundColor,
    disabledContentColor = CapitalColors.White
)
