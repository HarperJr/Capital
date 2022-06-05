package com.harper.core.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class CapitalShapes(
    /**
     * Small rounded shape 2dp
     */
    val small: CornerBasedShape = RoundedCornerShape(2.dp),
    /**
     * Medium rounded shape 4dp
     */
    val medium: CornerBasedShape = RoundedCornerShape(4.dp),
    /**
     * Large rounded shape 8dp
     */
    val large: CornerBasedShape = RoundedCornerShape(8.dp),
    /**
     * Extra large rounded shape 12dp
     */
    val extraLarge: CornerBasedShape = RoundedCornerShape(12.dp),
    /**
     * Bottom sheet rounded shape top left and right corners 12dp
     */
    val bottomSheet: CornerBasedShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
)

internal val LocalShapes = staticCompositionLocalOf { CapitalShapes() }
