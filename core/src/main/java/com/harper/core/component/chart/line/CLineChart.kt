package com.harper.core.component.chart.line

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
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
import com.harper.core.component.chart.line.drawer.SolidLineDrawer
import com.harper.core.component.chart.line.drawer.ValueDrawer
import com.harper.core.component.chart.line.drawer.XAxisDrawer
import com.harper.core.component.chart.line.shader.LineShader
import com.harper.core.component.chart.line.shader.NoLineShader
import com.harper.core.component.chart.line.shader.SolidLineShader
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch

@Composable
fun CLineChart(
    modifier: Modifier = Modifier,
    lineChartData: LineChartData,
    spacedByPercent: Float = 0.33f,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    lineDrawer: LineDrawer = SolidLineDrawer(thickness = lineChartData.thickness),
    lineShader: LineShader = if (lineChartData.fillLines) SolidLineShader() else NoLineShader,
    xAxisDrawer: XAxisDrawer = SimpleXAxisDrawer(),
    valueDrawer: ValueDrawer = SimpleValueDrawer()
) {
    require(spacedByPercent in 0.1f..0.75f) {
        "spacedBy must be in range from 0.1 to 0.75"
    }
    val state = rememberLineChartState(lineChartData, spacedByPercent)
    val linesSortedByMaxY = remember(lineChartData.lines) {
        lineChartData.lines.sortedByDescending { it.yMax }
    }
    val transition = remember(linesSortedByMaxY) { Animatable(initialValue = 0f) }
    val offset = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(linesSortedByMaxY) {
        transition.snapTo(0f)
        transition.animateTo(1f, animationSpec = animation)
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        snapshotFlow { offset.value }
            .filterNot { state.chartDrawableArea.isEmpty }
            .collect { offset ->
                state.updateByOffset(offset)
            }
    }
    val decayAnimationSpec = splineBasedDecay<Float>(LocalDensity.current)
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        offset.snapTo(offset.value + delta)
                    }
                },
                onDragStopped = { velocity ->
                    val result = offset.animateDecay(velocity, animationSpec = decayAnimationSpec)
                    if (result.endReason == AnimationEndReason.Finished) {
                        offset.animateTo(
                            state.selectedPosition,
                            animationSpec = spring(),
                            initialVelocity = result.endState.velocity
                        )
                    }
                }
            )
    ) {
        val xAxisDrawableArea = calculateXAxisDrawableArea(
            labelHeight = xAxisDrawer.requiredHeight(this),
            size = size
        )
        state.chartDrawableArea = calculateDrawableArea(size, xAxisDrawableArea)
        drawIntoCanvas { canvas ->
            linesSortedByMaxY.forEach { line ->
                lineDrawer.drawLine(
                    drawScope = this,
                    canvas = canvas,
                    linePath = calculateLinePath(
                        drawableArea = state.chartDrawableArea,
                        line = line,
                        spacedByPercent = spacedByPercent,
                        minYValue = lineChartData.minYValue,
                        yRange = lineChartData.yRange,
                        offset = offset.value,
                        transitionProgress = transition.value
                    ),
                    color = line.color
                )

                lineShader.fillLine(
                    drawScope = this,
                    canvas = canvas,
                    fillPath = calculateFillPath(
                        drawableArea = state.chartDrawableArea,
                        lineChartData = line,
                        spacedByPercent = spacedByPercent,
                        minYValue = lineChartData.minYValue,
                        yRange = lineChartData.yRange,
                        offset = offset.value,
                        transitionProgress = transition.value
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
                drawableArea = xAxisDrawableArea,
                offset = offset.value,
                spacedByPercent = spacedByPercent,
                labels = lineChartData.labels
            )
        }
    }
}

@Composable
private fun rememberLineChartState(lineChartData: LineChartData, spacedByPercent: Float): CLineChartState {
    return remember(lineChartData) { CLineChartState(lineChartData, spacedByPercent) }
}

class CLineChartState(private val lineChartData: LineChartData, private val spacedByPercent: Float) {
    var selectedPosition: Float by mutableStateOf(0f)
    var chartDrawableArea: Rect by mutableStateOf(Rect.Zero)

    private var itemWidth: Float = 0f

    fun updateByOffset(offset: Float) {
        if (itemWidth == 0f) {
            itemWidth = chartDrawableArea.width * spacedByPercent
        }
        val center = chartDrawableArea.center.x
        val indexes = lineChartData.lines.maxOf { it.points.size }
        for (i in 0 until indexes) {
            val isItemInCenter = ((itemWidth * i + offset) + itemWidth) / 2f - center <= itemWidth / 2f
            if (isItemInCenter) {
                selectedPosition = i * itemWidth
                break
            }
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

    private val yMin: Float
        get() = lines.minOfOrNull { it.yMin }.orElse(0f)
    private val yMax: Float
        get() = lines.maxOfOrNull { it.yMax }.orElse(1f)

    private val maxYValue: Float = yMax + ((yMax - yMin) * padBy / 100f)

    internal val minYValue: Float
        get() = if (startAtZero) 0f else yMin - ((yMax - yMin) * padBy / 100f)

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
