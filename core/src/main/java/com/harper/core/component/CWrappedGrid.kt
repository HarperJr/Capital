package com.harper.core.component

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints

@Composable
fun <T> CWrappedGrid(modifier: Modifier = Modifier, columns: Int, items: List<T>, item: @Composable (T) -> Unit) = Layout(
    content = {
        items.forEach {
            item.invoke(it)
        }
    },
    modifier = modifier.scrollable(rememberScrollState(), orientation = Orientation.Vertical),
    measurePolicy = { measurables, constraints ->
        val size: Int = if (constraints.hasBoundedWidth)
            (constraints.maxWidth / columns).coerceIn(constraints.minWidth, constraints.maxWidth) else -1
        val measurableConstraints = if (size == -1) constraints else Constraints.fixed(size, size)
        val placeables = measurables.map {
            it.measure(measurableConstraints)
        }
        val rows = placeables.size / columns + if ((placeables.size % columns) > 0) 1 else 0
        val layoutHeight = rows * size
        val layoutWidth = placeables.firstOrNull()?.width?.times(columns) ?: constraints.maxWidth
        layout(layoutWidth, layoutHeight) {
            placeables.forEachIndexed { i, placeable ->
                val row = i / columns
                val column = i % columns
                placeable.placeRelative(column * placeable.width, row * placeable.height)
            }
        }
    }
)