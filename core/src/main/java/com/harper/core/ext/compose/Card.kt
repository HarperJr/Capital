package com.harper.core.ext.compose

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt

@Stable
fun Modifier.assetCardSize(fraction: Float = 1f) =
    this.then(if (fraction == 1f) FillWholeMaxSize else createFillSizeModifier(fraction))

private const val cardWidth: Int = 292
private const val cardHeight: Int = 164
private const val cardHeightScaleFactor: Float = cardHeight.toFloat() / cardWidth.toFloat()

private val FillWholeMaxSize = createFillSizeModifier(1f)

@Suppress("ModifierFactoryExtensionFunction", "ModifierFactoryReturnType")
private fun createFillSizeModifier(fraction: Float) =
    FillModifier(
        fraction = fraction,
        inspectorInfo = {
            name = "fillMaxSize"
            properties["fraction"] = fraction
        }
    )

private class FillModifier(
    private val fraction: Float,
    inspectorInfo: InspectorInfo.() -> Unit
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val minWidth: Int
        val maxWidth: Int
        if (constraints.hasBoundedWidth) {
            val width = (constraints.maxWidth * fraction).roundToInt()
                .coerceIn(constraints.minWidth, constraints.maxWidth)
            minWidth = width
            maxWidth = width
        } else {
            minWidth = constraints.minWidth
            maxWidth = constraints.maxWidth
        }
        val minHeight: Int = (cardHeightScaleFactor * minWidth).roundToInt()
        val maxHeight: Int = (cardHeightScaleFactor * maxWidth).roundToInt()

        val placeable = measurable.measure(
            Constraints(minWidth, maxWidth, minHeight, maxHeight)
        )

        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}
