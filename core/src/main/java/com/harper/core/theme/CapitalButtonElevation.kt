package com.harper.core.theme

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun capitalButtonElevation(): ButtonElevation = ButtonDefaults.elevation(
    defaultElevation = 4.dp,
    pressedElevation = 6.dp,
    disabledElevation = 0.dp
)

@Composable
fun capitalButtonBorderlessElevation(): ButtonElevation = ButtonDefaults.elevation(
    defaultElevation = 0.dp,
    pressedElevation = 0.dp,
    disabledElevation = 0.dp
)
