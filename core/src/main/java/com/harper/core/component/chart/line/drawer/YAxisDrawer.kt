package com.harper.core.component.chart.line.drawer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harper.core.component.chart.line.CLineChartUtils.toLegacyInt
import java.lang.Integer.max
import kotlin.math.roundToInt

interface YAxisDrawer {

    fun drawAxisLines(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        offset: Float,
        spacedByPercent: Float,
        labels: List<String>
    )

    fun drawAxisLabels(drawScope: DrawScope, canvas: Canvas, drawableArea: Rect, minValue: Float, maxValue: Float)
}

typealias LabelFormatter = (value: Float) -> String

class SimpleYAxisDrawer(
    private val labelTextSize: TextUnit = 12.sp,
    private val labelTextColor: Color = Color.Black,
    private val labelRatio: Int = 3,
    private val labelValueFormatter: LabelFormatter = { value -> "%.1f".format(value) },
    private val axisLineThickness: Dp = 1.dp,
    private val axisLineColor: Color = Color.Black
) : YAxisDrawer {
    private val axisLinePaint = Paint().apply {
        isAntiAlias = true
        color = axisLineColor
        style = PaintingStyle.Stroke
    }
    private val textPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        color = labelTextColor.toLegacyInt()
    }
    private val textBounds = android.graphics.Rect()

    override fun drawAxisLines(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        offset: Float,
        spacedByPercent: Float,
        labels: List<String>
    ) = with(drawScope) {
        val lineThickness = axisLineThickness.toPx()
        val axisSpace = drawableArea.width * spacedByPercent
        repeat(labels.size) { index ->
            val x = drawableArea.right + (index - labels.size) * axisSpace + offset + axisSpace / 2
            canvas.drawLine(
                Offset(x, drawableArea.bottom),
                Offset(x, drawableArea.top),
                axisLinePaint.apply {
                    strokeWidth = lineThickness
                }
            )
        }
    }

    override fun drawAxisLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        minValue: Float,
        maxValue: Float
    ) = with(drawScope) {
        val labelPaint = textPaint.apply {
            textSize = labelTextSize.toPx()
            textAlign = android.graphics.Paint.Align.RIGHT
        }
        val minLabelHeight = (labelTextSize.toPx() * labelRatio.toFloat())
        val totalHeight = drawableArea.height
        val labelCount = max((drawableArea.height / minLabelHeight).roundToInt(), 2)

        for (i in 0..labelCount) {
            val value = minValue + (i * ((maxValue - minValue) / labelCount))

            val label = labelValueFormatter(value)
            val x = drawableArea.right - axisLineThickness.toPx() - (labelTextSize.toPx() / 2f)

            labelPaint.getTextBounds(label, 0, label.length, textBounds)
            val y = drawableArea.bottom - (i * (totalHeight / labelCount)) + (textBounds.height() / 2f)

            canvas.nativeCanvas.drawText(label, x, y, labelPaint)
        }
    }
}
