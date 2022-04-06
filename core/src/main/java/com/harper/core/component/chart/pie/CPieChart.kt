package com.harper.core.component.chart.pie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import com.harper.core.component.CPreview
import com.harper.core.component.chart.line.CLineChartUtils.simpleChartAnimation
import com.harper.core.component.chart.pie.PieChartUtils.calculateAngle
import com.harper.core.component.chart.pie.drawer.SliceDrawer
import com.harper.core.component.chart.pie.drawer.SolidSliceDrawer

@Composable
fun CPieChart(
    pieChartData: PieChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    sliceDrawer: SliceDrawer = SolidSliceDrawer()
) {
    val transitionProgress = remember(pieChartData.slices) { Animatable(initialValue = 0f) }

    // When slices value changes we want to re-animated the chart.
    LaunchedEffect(pieChartData.slices) {
        transitionProgress.animateTo(1f, animationSpec = animation)
    }

    DrawChart(
        pieChartData = pieChartData,
        modifier = modifier.fillMaxSize(),
        progress = transitionProgress.value,
        sliceDrawer = sliceDrawer
    )
}

@Composable
private fun DrawChart(
    pieChartData: PieChartData,
    modifier: Modifier,
    progress: Float,
    sliceDrawer: SliceDrawer
) {
    val slices = pieChartData.slices

    Canvas(modifier = modifier) {
        drawIntoCanvas {
            var startArc = 0f

            slices.forEach { slice ->
                val arc = calculateAngle(
                    sliceLength = slice.value,
                    totalLength = pieChartData.totalSize,
                    progress = progress
                )

                sliceDrawer.drawSlice(
                    drawScope = this,
                    canvas = drawContext.canvas,
                    area = size,
                    startAngle = startArc,
                    sweepAngle = arc,
                    slice = slice
                )

                startArc += arc
            }
        }
    }
}

data class PieChartData(
    val slices: List<Slice>
) {
    internal val totalSize: Float
        get() {
            var total = 0f
            slices.forEach { total += it.value }
            return total
        }

    data class Slice(
        val value: Float,
        val color: Color
    )
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() {
    CPreview {
        CPieChart(
            pieChartData = PieChartData(
                slices = listOf(
                    PieChartData.Slice(25f, Color.Red),
                    PieChartData.Slice(42f, Color.Blue),
                    PieChartData.Slice(23f, Color.Green)
                )
            )
        )
    }
}