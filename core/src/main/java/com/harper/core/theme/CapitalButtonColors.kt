package com.harper.core.theme

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

@Composable
fun capitalButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = CapitalColors.DeepBlue,
    contentColor = CapitalColors.White,
    disabledBackgroundColor = CapitalColors.LightBlue,
    disabledContentColor = CapitalColors.White
)

@Composable
fun capitalButtonBorderlessColors(): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = CapitalColors.Transparent,
    contentColor = CapitalColors.Transparent,
    disabledBackgroundColor = CapitalColors.Transparent,
    disabledContentColor = CapitalColors.Grey
)
