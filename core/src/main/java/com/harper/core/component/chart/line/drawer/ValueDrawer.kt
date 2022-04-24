package com.harper.core.component.chart.line.drawer

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope

interface ValueDrawer {

    fun drawValue(drawScope: CanvasDrawScope, canvas: Canvas, drawableArea: Rect, value: Float)
}

class SimpleValueDrawer : ValueDrawer {

    override fun drawValue(drawScope: CanvasDrawScope, canvas: Canvas, drawableArea: Rect, value: Float) {

    }
}
