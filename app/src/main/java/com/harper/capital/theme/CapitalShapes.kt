package com.harper.capital.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
class CapitalShapes(

    val small: CornerBasedShape = RoundedCornerShape(4.dp),

    val medium: CornerBasedShape = RoundedCornerShape(4.dp),

    val large: CornerBasedShape = RoundedCornerShape(0.dp)
) {

    /**
     * Returns a copy of this Shapes, optionally overriding some of the values.
     */
    fun copy(
        small: CornerBasedShape = this.small,
        medium: CornerBasedShape = this.medium,
        large: CornerBasedShape = this.large
    ): CapitalShapes = CapitalShapes(
        small = small,
        medium = medium,
        large = large
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CapitalShapes) return false

        if (small != other.small) return false
        if (medium != other.medium) return false
        if (large != other.large) return false

        return true
    }

    override fun hashCode(): Int {
        var result = small.hashCode()
        result = 31 * result + medium.hashCode()
        result = 31 * result + large.hashCode()
        return result
    }

    override fun toString(): String {
        return "CapitalShapes(small=$small, medium=$medium, large=$large)"
    }
}

internal val LocalShapes = staticCompositionLocalOf { CapitalShapes() }
