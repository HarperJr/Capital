package com.harper.core.component.chart.line

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import kotlin.math.max
import kotlin.math.roundToInt

internal class CLineChartState(
    lineChartData: LineChartData,
    private val spacedByPercent: Float,
    private val decayAnimationSpec: DecayAnimationSpec<Float>
) {
    internal val offset: Float get() = offsetAnim.value
    internal var chartArea: Rect by mutableStateOf(Rect.Zero)

    private val offsetAnim = Animatable(initialValue = 0f)

    private val itemWidth: Float
        get() = chartArea.width * spacedByPercent

    suspend fun scroll(delta: Float) {
        offsetAnim.snapTo(offsetAnim.value + delta)
    }

    suspend fun applyVelocity(velocity: Float) {
        val result = offsetAnim.animateDecay(velocity.coerceIn(-0.5f, 0.5f), decayAnimationSpec)
        if (result.endReason == AnimationEndReason.Finished) {
            val targetPosition = (result.endState.value / itemWidth).roundToInt() * itemWidth
            offsetAnim.animateTo(
                targetPosition,
                animationSpec = spring(),
                initialVelocity = result.endState.velocity
            )
        }
    }
}
