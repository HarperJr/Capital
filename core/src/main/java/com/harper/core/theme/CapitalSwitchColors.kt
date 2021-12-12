package com.harper.core.theme

import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable

@Composable
fun capitalSwitchColors(): SwitchColors = SwitchDefaults.colors(
    checkedThumbColor = CapitalColors.DodgerBlue,
    uncheckedThumbColor = CapitalColors.White,
    checkedTrackColor = CapitalColors.CornflowerBlue,
    uncheckedTrackColor = CapitalColors.Boulder,
)