package com.harper.core.theme

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable

@Composable
fun capitalButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = CapitalColors.Blue,
    contentColor = CapitalColors.White,
    disabledBackgroundColor = CapitalColors.BlueLight,
    disabledContentColor = CapitalColors.White
)

@Composable
fun capitalButtonBorderlessColors(): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = CapitalColors.Transparent,
    contentColor = CapitalColors.Transparent,
    disabledBackgroundColor = CapitalColors.Transparent,
    disabledContentColor = CapitalColors.GreyLight
)
