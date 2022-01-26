package com.harper.core.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class CapitalDimensions(
    val side: Dp,
    val tiny: Dp,
    val small: Dp,
    val extraSmall: Dp,
    val medium: Dp,
    val large: Dp,
    val largest: Dp,
    val imageSmall: Dp,
    val imageMedium: Dp,
    val imageLarge: Dp
)

fun dimensions(): CapitalDimensions = CapitalDimensions(
    side = 16.dp,
    tiny = 4.dp,
    small = 8.dp,
    extraSmall = 12.dp,
    medium = 16.dp,
    large = 24.dp,
    largest = 32.dp,
    imageSmall = 16.dp,
    imageMedium = 32.dp,
    imageLarge = 64.dp
)

val LocalDimensions = compositionLocalOf { dimensions() }
