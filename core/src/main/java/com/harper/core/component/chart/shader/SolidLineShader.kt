package com.harper.core.component.chart.shader

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawscope.DrawScope

class SolidLineShader : LineShader {

    override fun fillLine(drawScope: DrawScope, canvas: Canvas, fillPath: Path, color: Color) {
        drawScope.drawPath(path = fillPath, color = color.compositeOver(Color.Black))
    }
}
