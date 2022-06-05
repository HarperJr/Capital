package com.harper.core.theme

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun capitalButtonElevation(elevation: Dp): ButtonElevation = ButtonDefaults.elevation(
    defaultElevation = elevation,
    pressedElevation = elevation,
    disabledElevation = elevation
)
