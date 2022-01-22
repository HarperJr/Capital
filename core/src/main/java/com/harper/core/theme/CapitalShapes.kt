package com.harper.core.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class CapitalShapes(
    val small: CornerBasedShape = RoundedCornerShape(2.dp),
    val medium: CornerBasedShape = RoundedCornerShape(4.dp),
    val large: CornerBasedShape = RoundedCornerShape(8.dp),
    val extraLarge: CornerBasedShape = RoundedCornerShape(12.dp),
    val bottomSheet: CornerBasedShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
)

internal val LocalShapes = staticCompositionLocalOf { CapitalShapes() }
