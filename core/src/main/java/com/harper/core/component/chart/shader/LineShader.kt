package com.harper.core.component.chart.shader

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

interface LineShader {

    fun fillLine(drawScope: DrawScope, canvas: Canvas, fillPath: Path, color: Color)
}

object NoLineShader : LineShader {

    override fun fillLine(drawScope: DrawScope, canvas: Canvas, fillPath: Path, color: Color) {
        // Do nothing
    }
}
