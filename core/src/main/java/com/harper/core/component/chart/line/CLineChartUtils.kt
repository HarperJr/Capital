package com.harper.core.component.chart.line

import androidx.compose.animation.core.TweenSpec
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

object CLineChartUtils {

    fun calculateDrawableArea(size: Size, xAxisDrawableArea: Rect): Rect =
        Rect(left = 0f, top = 0f, bottom = xAxisDrawableArea.top, right = size.width)

    fun calculateXAxisDrawableArea(height: Float, size: Size): Rect =
        Rect(left = 0f, top = size.height - height, bottom = size.height, right = size.width)

    private fun calculatePointLocation(
        lineChartData: LineChartData.Line,
        drawableArea: Rect,
        spacedByPercent: Float,
        point: Float,
        minYValue: Float,
        yRange: Float,
        offset: Float,
        index: Int
    ): Offset {
        val pointAreaWidth = drawableArea.width * spacedByPercent
        val x = (index.toFloat() - lineChartData.points.size) * pointAreaWidth + pointAreaWidth / 2f
        val y = ((point - minYValue) / yRange)

        return Offset(
            x = x + drawableArea.right + offset,
            y = drawableArea.height - (y * drawableArea.height)
        )
    }

    private fun withProgress(
        index: Int,
        lineChartData: LineChartData.Line,
        transitionProgress: Float,
        showWithProgress: (progress: Float) -> Unit
    ) {
        val size = lineChartData.points.size
        val toIndex = (size * transitionProgress).toInt() + 1

        if (index == toIndex) {
            // Get the left over.
            val sizeF = lineChartData.points.size.toFloat()
            val perIndex = (1f / sizeF)
            val down = (index - 1) * perIndex
            showWithProgress((transitionProgress - down) / perIndex)
        } else if (index < toIndex) {
            showWithProgress(1f)
        }
    }

    fun calculateLinePath(
        drawableArea: Rect,
        line: LineChartData.Line,
        spacedByPercent: Float,
        minYValue: Float,
        yRange: Float,
        offset: Float,
        transitionProgress: Float
    ): Path = Path().apply {
        var prevPointLocation: Offset? = null
        line.points.forEachIndexed { index, point ->
            withProgress(
                index = index,
                transitionProgress = transitionProgress,
                lineChartData = line
            ) { progress ->
                val pointLocation = calculatePointLocation(
                    lineChartData = line,
                    drawableArea = drawableArea,
                    spacedByPercent = spacedByPercent,
                    point = point,
                    minYValue,
                    yRange,
                    offset,
                    index = index
                )

                if (index == 0) {
                    moveTo(pointLocation.x, pointLocation.y)
                } else {
                    if (progress <= 1f) {
                        // We have to change the `dy` based on the progress
                        val prevX = prevPointLocation!!.x
                        val prevY = prevPointLocation!!.y

                        val x = (pointLocation.x - prevX) * progress + prevX
                        val y = (pointLocation.y - prevY) * progress + prevY

                        lineTo(x, y)
                    } else {
                        lineTo(pointLocation.x, pointLocation.y)
                    }
                }

                prevPointLocation = pointLocation
            }
        }
    }

    fun calculateFillPath(
        drawableArea: Rect,
        line: LineChartData.Line,
        spacedByPercent: Float,
        minYValue: Float,
        yRange: Float,
        offset: Float,
        transitionProgress: Float
    ): Path = Path().apply {

        // we start from the bottom left
        moveTo(drawableArea.left, drawableArea.bottom)
        var prevPointX: Float? = null
        var prevPointLocation: Offset? = null
        line.points.forEachIndexed { index, point ->
            withProgress(
                index = index,
                transitionProgress = transitionProgress,
                lineChartData = line
            ) { progress ->
                val pointLocation = calculatePointLocation(
                    drawableArea = drawableArea,
                    lineChartData = line,
                    spacedByPercent = spacedByPercent,
                    point = point,
                    minYValue = minYValue,
                    yRange = yRange,
                    offset = offset,
                    index = index
                )

                if (index == 0) {
                    lineTo(drawableArea.left, pointLocation.y)
                    lineTo(pointLocation.x, pointLocation.y)
                } else {
                    prevPointX = if (progress <= 1f) {
                        // We have to change the `dy` based on the progress
                        val prevX = prevPointLocation!!.x
                        val prevY = prevPointLocation!!.y

                        val x = (pointLocation.x - prevX) * progress + prevX
                        val y = (pointLocation.y - prevY) * progress + prevY

                        lineTo(x, y)

                        x
                    } else {
                        lineTo(pointLocation.x, pointLocation.y)
                        pointLocation.x
                    }
                }

                prevPointLocation = pointLocation
            }
        }
        // We need to connect the line to the end of the drawable area
        prevPointX?.let { x ->
            lineTo(x, drawableArea.bottom)
            lineTo(drawableArea.right, drawableArea.bottom)
        } ?: lineTo(drawableArea.left, drawableArea.bottom)
    }

    fun simpleChartAnimation() = TweenSpec<Float>(durationMillis = 500)

    fun Color.toLegacyInt(): Int {
        return android.graphics.Color.argb(
            (alpha * 255.0f + 0.5f).toInt(),
            (red * 255.0f + 0.5f).toInt(),
            (green * 255.0f + 0.5f).toInt(),
            (blue * 255.0f + 0.5f).toInt()
        )
    }
}