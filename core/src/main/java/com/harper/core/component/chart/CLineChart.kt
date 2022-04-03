package com.harper.core.component.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.harper.core.component.chart.CLineChartUtils.calculateDrawableArea
import com.harper.core.component.chart.CLineChartUtils.calculateFillPath
import com.harper.core.component.chart.CLineChartUtils.calculateLinePath
import com.harper.core.component.chart.CLineChartUtils.calculateXAxisDrawableArea
import com.harper.core.component.chart.CLineChartUtils.calculateXAxisLabelsDrawableArea
import com.harper.core.component.chart.CLineChartUtils.calculateYAxisDrawableArea
import com.harper.core.component.chart.CLineChartUtils.simpleChartAnimation
import com.harper.core.component.chart.drawer.LineDrawer
import com.harper.core.component.chart.drawer.SimpleXAxisDrawer
import com.harper.core.component.chart.drawer.SimpleYAxisDrawer
import com.harper.core.component.chart.drawer.SolidLineDrawer
import com.harper.core.component.chart.drawer.XAxisDrawer
import com.harper.core.component.chart.drawer.YAxisDrawer
import com.harper.core.component.chart.shader.LineShader
import com.harper.core.component.chart.shader.NoLineShader
import com.harper.core.component.chart.shader.SolidLineShader
import com.harper.core.theme.CapitalTheme

@Composable
fun CLineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    lineDrawer: LineDrawer = SolidLineDrawer(thickness = lineChartData.thickness),
    lineShader: LineShader = if (lineChartData.fillLines) SolidLineShader() else NoLineShader,
    xAxisDrawer: XAxisDrawer = SimpleXAxisDrawer(),
    yAxisDrawer: YAxisDrawer = SimpleYAxisDrawer(),
    horizontalOffset: Float = 5f
) {
    check(horizontalOffset in 0f..25f) {
        "Horizontal offset is the % offset from sides, " +
            "and should be between 0%-25%"
    }

    val linesSortedByMaxY = remember(lineChartData.lines) {
        lineChartData.lines.sortedByDescending { it.yMinMax.second }
    }
    val transitionAnimation = remember(linesSortedByMaxY) { Animatable(initialValue = 0f) }

    LaunchedEffect(linesSortedByMaxY) {
        transitionAnimation.snapTo(0f)
        transitionAnimation.animateTo(1f, animationSpec = animation)
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            val yAxisDrawableArea = calculateYAxisDrawableArea(
                xAxisLabelSize = xAxisDrawer.requiredHeight(this),
                size = size
            )
            val xAxisDrawableArea = calculateXAxisDrawableArea(
                yAxisWidth = yAxisDrawableArea.width,
                labelHeight = xAxisDrawer.requiredHeight(this),
                size = size
            )
            val xAxisLabelsDrawableArea = calculateXAxisLabelsDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                offset = horizontalOffset
            )
            val chartDrawableArea = calculateDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                yAxisDrawableArea = yAxisDrawableArea,
                size = size,
                offset = horizontalOffset
            )

            linesSortedByMaxY.forEach { line ->
                lineDrawer.drawLine(
                    drawScope = this,
                    canvas = canvas,
                    linePath = calculateLinePath(
                        drawableArea = chartDrawableArea,
                        lineChartData = line,
                        minYValue = lineChartData.minYValue,
                        yRange = lineChartData.yRange,
                        transitionProgress = transitionAnimation.value
                    ),
                    color = line.color
                )

                lineShader.fillLine(
                    drawScope = this,
                    canvas = canvas,
                    fillPath = calculateFillPath(
                        drawableArea = chartDrawableArea,
                        lineChartData = line,
                        minYValue = lineChartData.minYValue,
                        yRange = lineChartData.yRange,
                        transitionProgress = transitionAnimation.value
                    ),
                    color = line.color,
                )
            }

            // Draw the X Axis line.
            xAxisDrawer.drawAxisLine(
                drawScope = this,
                drawableArea = xAxisDrawableArea,
                canvas = canvas
            )

            xAxisDrawer.drawAxisLabels(
                drawScope = this,
                canvas = canvas,
                drawableArea = xAxisLabelsDrawableArea,
                labels = lineChartData.labels
            )

            // Draw the Y Axis line.
            yAxisDrawer.drawAxisLine(
                drawScope = this,
                canvas = canvas,
                drawableArea = yAxisDrawableArea
            )

            yAxisDrawer.drawAxisLabels(
                drawScope = this,
                canvas = canvas,
                drawableArea = yAxisDrawableArea,
                minValue = lineChartData.minYValue,
                maxValue = lineChartData.maxYValue
            )
        }
    }
}

class LineChartData(
    val lines: List<Line>,
    val labels: List<String>,
    val fillLines: Boolean = false,
    val thickness: Dp = 1.dp,
    private val padBy: Float = 20f,
    private val startAtZero: Boolean = false
) {
    init {
        require(padBy in 0f..100f)
    }

    private val yMinMax: Pair<Float, Float>
        get() {
            val min = lines.minOf { it.yMinMax.first }
            val max = lines.maxOf { it.yMinMax.second }
            return min to max
        }

    internal val maxYValue: Float =
        yMinMax.second + ((yMinMax.second - yMinMax.first) * padBy / 100f)

    internal val minYValue: Float
        get() {
            return if (startAtZero) {
                0f
            } else {
                yMinMax.first - ((yMinMax.second - yMinMax.first) * padBy / 100f)
            }
        }
    internal val yRange = maxYValue - minYValue

    data class Line(val points: List<Float>, val color: Color) {
        internal val yMinMax: Pair<Float, Float>
            get() {
                val min = points.minOf { it }
                val max = points.maxOf { it }
                return min to max
            }
    }
}

@Preview(showBackground = true)
@Composable
fun CLineChartLight() {
    CapitalTheme {
        CLineChart(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            lineChartData = LineChartData(
                lines = listOf(
                    LineChartData.Line(
                        points = listOf(0.5f, 0.4f, 0.3f, 0.5f, 0.6f, 0.2f, 0.2f, 0.4f, 0.8f),
                        color = Color.DarkGray
                    ),
                    LineChartData.Line(
                        points = listOf(1.5f, 0.8f, 0.6f, 0.2f, 0.6f, 1.2f, 1.2f, 1.4f, 1.8f),
                        color = Color.Black
                    )
                ),
                labels = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep")
            ),
            lineShader = SolidLineShader()
        )
    }
}
