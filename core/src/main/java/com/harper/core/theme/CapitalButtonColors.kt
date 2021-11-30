package com.harper.core.theme

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable

@Composable
fun capitalButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = CapitalColors.DodgerBlue,
    contentColor = CapitalColors.White,
    disabledBackgroundColor = CapitalColors.CornflowerBlue,
    disabledContentColor = CapitalColors.White
)

@Composable
fun capitalButtonBorderlessColors(): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = CapitalColors.Transparent,
    contentColor = CapitalColors.Transparent,
    disabledBackgroundColor = CapitalColors.Transparent,
    disabledContentColor = CapitalColors.Silver
)
