package com.harper.core.component.chart.line

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.harper.core.ext.orElse
import kotlin.math.abs

internal class CLineChartState(
    lineChartData: LineChartData,
    private val spacedByPercent: Float,
    private val decayAnimationSpec: DecayAnimationSpec<Float>
) {
    val snappedPosition: Float get() = position * itemWidth

    val offset: Float get() = offsetAnim.value

    var position: Int by mutableStateOf(0)
    var chartArea: Rect by mutableStateOf(Rect.Zero)

    private val offsetAnim = Animatable(initialValue = 0f)
    private val indexes: Int = lineChartData.lines.maxOfOrNull { it.points.size }.orElse(0)

    private var itemWidth: Float = 0f

    fun updateChartDrawableArea(area: Rect) {
        chartArea = area
        if (itemWidth == 0f) {
            itemWidth = chartArea.width * spacedByPercent
        }
    }

    suspend fun scroll(delta: Float) {
        offsetAnim.snapTo(offsetAnim.value + delta)
        onScroll()
    }

    suspend fun applyVelocity(velocity: Float) {
        val result = offsetAnim.animateDecay(velocity, animationSpec = decayAnimationSpec)
        if (result.endReason == AnimationEndReason.Finished) {
            offsetAnim.animateTo(
                snappedPosition,
                animationSpec = spring(),
                initialVelocity = result.endState.velocity
            )
        }
    }

    private fun onScroll() {
        if (chartArea.isEmpty) {
            return
        }
        for (i in 0 until indexes) {
            val isItemInCenter = abs(offset + itemWidth / 2f - chartArea.center.x) <= itemWidth / 2f
            if (isItemInCenter) {
                position = i
                break
            }
        }
    }
}
