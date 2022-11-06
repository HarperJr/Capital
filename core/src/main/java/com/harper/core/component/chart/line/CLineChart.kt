package com.harper.core.component.chart.line

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.harper.core.component.CPreview
import com.harper.core.component.chart.line.CLineChartUtils.calculateDrawableArea
import com.harper.core.component.chart.line.CLineChartUtils.calculateFillPath
import com.harper.core.component.chart.line.CLineChartUtils.calculateLinePath
import com.harper.core.component.chart.line.CLineChartUtils.calculateXAxisDrawableArea
import com.harper.core.component.chart.line.CLineChartUtils.simpleChartAnimation
import com.harper.core.component.chart.line.drawer.LineDrawer
import com.harper.core.component.chart.line.drawer.SimpleValueDrawer
import com.harper.core.component.chart.line.drawer.SimpleXAxisDrawer
import com.harper.core.component.chart.line.drawer.SimpleYAxisDrawer
import com.harper.core.component.chart.line.drawer.SolidLineDrawer
import com.harper.core.component.chart.line.drawer.ValueDrawer
import com.harper.core.component.chart.line.drawer.XAxisDrawer
import com.harper.core.component.chart.line.drawer.YAxisDrawer
import com.harper.core.component.chart.line.shader.LineShader
import com.harper.core.component.chart.line.shader.NoLineShader
import com.harper.core.component.chart.line.shader.SolidLineShader
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalTheme
import kotlinx.coroutines.launch

@Composable
fun CLineChart(
    modifier: Modifier = Modifier,
    lineChartData: LineChartData,
    @FloatRange(from = 0.33, to = 0.75) spacedByPercent: Float = 0.33f,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    lineDrawer: LineDrawer = SolidLineDrawer(thickness = lineChartData.thickness),
    lineShader: LineShader = if (lineChartData.fillLines) SolidLineShader() else NoLineShader,
    xAxisDrawer: XAxisDrawer = SimpleXAxisDrawer(),
    yAxisDrawer: YAxisDrawer = SimpleYAxisDrawer(),
    valueDrawer: ValueDrawer = SimpleValueDrawer()
) {
    val state = rememberLineChartState(lineChartData, spacedByPercent)
    val coroutineScope = rememberCoroutineScope()
    val transitionAnim = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(lineChartData) {
        transitionAnim.snapTo(0f)
        transitionAnim.animateTo(1f, animationSpec = animation)
    }
    val sortedLines = remember(lineChartData) { lineChartData.lines.sortedByDescending { it.yMax } }
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta -> coroutineScope.launch { state.scroll(delta) } },
                onDragStopped = { velocity -> state.applyVelocity(velocity) }
            )
    ) {
        val xAxisDrawableArea = calculateXAxisDrawableArea(height = xAxisDrawer.measureHeight(this), size = size)
        state.chartArea = calculateDrawableArea(size, xAxisDrawableArea)
        drawIntoCanvas { canvas ->
            sortedLines.forEach { line ->
                lineDrawer.draw(
                    drawScope = this,
                    canvas = canvas,
                    linePath = calculateLinePath(
                        drawableArea = state.chartArea,
                        line = line,
                        spacedByPercent = spacedByPercent,
                        minYValue = lineChartData.minYValue,
                        yRange = lineChartData.yRange,
                        offset = state.offset,
                        transitionProgress = transitionAnim.value
                    ),
                    color = line.color
                )
                lineShader.fillLine(
                    drawScope = this,
                    canvas = canvas,
                    fillPath = calculateFillPath(
                        drawableArea = state.chartArea,
                        line = line,
                        spacedByPercent = spacedByPercent,
                        minYValue = lineChartData.minYValue,
                        yRange = lineChartData.yRange,
                        offset = state.offset,
                        transitionProgress = transitionAnim.value
                    ),
                    color = line.color,
                )
            }
            yAxisDrawer.drawAxisLines(
                drawScope = this,
                canvas = canvas,
                drawableArea = state.chartArea,
                offset = state.offset,
                spacedByPercent = spacedByPercent,
                labels = lineChartData.labels
            )
            // Draw the X Axis line.
            xAxisDrawer.drawAxisLine(
                drawScope = this,
                drawableArea = xAxisDrawableArea,
                canvas = canvas
            )
            xAxisDrawer.drawAxisLabels(
                drawScope = this,
                canvas = canvas,
                drawableArea = xAxisDrawableArea,
                offset = state.offset,
                spacedByPercent = spacedByPercent,
                labels = lineChartData.labels
            )
        }
    }
}

@Composable
private fun rememberLineChartState(lineChartData: LineChartData, spacedByPercent: Float): CLineChartState {
    val decayAnimationSpec = splineBasedDecay<Float>(LocalDensity.current)
    return remember(lineChartData, spacedByPercent) {
        CLineChartState(lineChartData, spacedByPercent, decayAnimationSpec)
    }
}

class LineChartData(
    val lines: List<Line>,
    val labels: List<String>,
    val fillLines: Boolean = false,
    val thickness: Dp = 1.dp,
    padBy: Float = 20f,
    startAtZero: Boolean = false
) {
    init {
        require(padBy in 0f..100f)
    }

    private val yMin: Float
        get() = lines.minOfOrNull { it.yMin }.orElse(0f)
    private val yMax: Float
        get() = lines.maxOfOrNull { it.yMax }.orElse(1f)

    private val maxYValue: Float = yMax + ((yMax - yMin) * padBy / 100f)

    internal val minYValue: Float = if (startAtZero) 0f else yMin - ((yMax - yMin) * padBy / 100f)

    internal val yRange = maxYValue - minYValue

    data class Line(val points: List<Float>, val color: Color) {
        internal val yMin: Float
            get() = points.minOfOrNull { it }.orElse(0f)
        internal val yMax: Float
            get() = points.maxOfOrNull { it }.orElse(1f)
    }
}

@Preview(showBackground = true)
@Composable
fun CLineChartLight() {
    CPreview {
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
