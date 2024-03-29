package com.harper.core.component.chart.line.drawer

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface LineDrawer {

    fun draw(drawScope: DrawScope, canvas: Canvas, linePath: Path, color: Color)
}

data class SolidLineDrawer(
    val thickness: Dp = 3.dp,
    val color: Color = Color.Cyan
) : LineDrawer {
    private val paint = Paint().apply {
        this.color = this@SolidLineDrawer.color
        this.style = PaintingStyle.Stroke
        this.isAntiAlias = true
    }

    override fun draw(
        drawScope: DrawScope,
        canvas: Canvas,
        linePath: Path,
        color: Color
    ) {
        val lineThickness = with(drawScope) { thickness.toPx() }
        canvas.drawPath(
            path = linePath,
            paint = paint.apply {
                this.color = color
                strokeWidth = lineThickness
            }
        )
    }
}
