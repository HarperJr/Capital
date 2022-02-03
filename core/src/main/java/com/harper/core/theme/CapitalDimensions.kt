package com.harper.core.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class CapitalDimensions(
    val small: Dp,
    val medium: Dp,
    val side: Dp,
    val large: Dp,
    val largest: Dp,
    val imageSmall: Dp,
    val imageMedium: Dp,
    val imageLarge: Dp,
    val imageLargest: Dp
)

fun dimensions(): CapitalDimensions = CapitalDimensions(
    small = 4.dp,
    medium = 8.dp,
    side = 16.dp,
    large = 24.dp,
    largest = 32.dp,
    imageSmall = 16.dp,
    imageMedium = 32.dp,
    imageLarge = 44.dp,
    imageLargest = 64.dp
)

val LocalDimensions = compositionLocalOf { dimensions() }
