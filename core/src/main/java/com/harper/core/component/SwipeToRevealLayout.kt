package com.harper.core.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalTheme
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private const val TRANSITION_DURATION = 300
private const val REVEAL_VELOCITY = 1f

/**
 * Layout which allows to make swipe to reveal behavior: Content to show on left or right side when layout is swiped.
 * Use [state] to set [SwipeDirection] by horizontal axis or animate reveal/dismiss.
 * Place [revealContent] such as controls or icons which will be hidden or shown otherwise when state is changed.
 * Place [swipeContent] which is your main content of a component
 */
@Composable
fun SwipeToRevealLayout(
    state: SwipeToRevealLayoutState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onDismissed: (() -> Unit)? = null,
    onRevealed: (() -> Unit)? = null,
    revealContent: @Composable () -> Unit,
    swipeContent: @Composable () -> Unit
) {
    LaunchedEffect(Unit) {
        snapshotFlow { state.isRevealed }
            .collectLatest { isRevealed ->
                if (isRevealed) onRevealed?.invoke() else onDismissed?.invoke()
            }
    }
    SubcomposeLayout(
        modifier = modifier
            .draggable(
                rememberDraggableState(onDelta = { delta -> state.consumeDelta(delta) }),
                orientation = Orientation.Horizontal,
                enabled = enabled,
                onDragStopped = { state.updateStateOnDragStopped() }
            ),
        measurePolicy = { constraints ->
            val contentPlaceables =
                subcompose(SwipeToDismissLayout.CONTENT, swipeContent).map { it.measure(constraints) }
            val width = if (constraints.hasBoundedWidth) {
                contentPlaceables.maxOf { it.width }.coerceIn(constraints.minWidth, constraints.maxWidth)
            } else {
                constraints.maxWidth
            }
            val height = if (constraints.hasBoundedHeight) {
                contentPlaceables.maxOf { it.height }.coerceIn(constraints.minHeight, constraints.maxHeight)
            } else {
                constraints.maxHeight
            }
            val revealablePlaceables = subcompose(SwipeToDismissLayout.REVEALABLE, revealContent).map {
                it.measure(constraints.copy(maxHeight = height, minWidth = 0))
            }
            val maxOffset = revealablePlaceables
                .sumOf { it.width }
                .coerceIn(0, width)
                .toFloat()
            state.setMaxOffset(maxOffset)
            layout(width, height) {
                revealablePlaceables.fold(0) { offset, next ->
                    val directionOffset = if (state.isLeftToRight) 0 else width - next.width
                    next.place(directionOffset + (offset * state.swipeProgress).roundToInt(), 0)
                    next.width
                }
                contentPlaceables.forEach { it.place(state.offsetX.roundToInt(), 0) }
            }
        }
    )
}

@Composable
fun rememberSwipeToRevealLayoutState(direction: SwipeDirection) = remember { SwipeToRevealLayoutState(direction) }

class SwipeToRevealLayoutState(direction: SwipeDirection) {
    var isRevealed by mutableStateOf(false)
        private set
    var offsetX by mutableStateOf(0f)
        private set

    val swipeProgress: Float
        get() = if (isLeftToRight) offsetX / maxOffsetX else -(offsetX / maxOffsetX)
    internal val isLeftToRight = direction == SwipeDirection.LTR
    private var maxOffsetX: Float = 0f

    suspend fun animateReveal() {
        animateDrag(maxOffsetX)
        isRevealed = true
    }

    suspend fun animateDismiss() {
        animateDrag(0f)
        isRevealed = false
    }

    fun consumeDelta(delta: Float) {
        val newOffset = offsetX + delta
        offsetX = if (isLeftToRight) {
            newOffset.coerceIn(0f, maxOffsetX)
        } else {
            newOffset.coerceIn(maxOffsetX, 0f)
        }
    }

    internal fun setMaxOffset(max: Float) {
        maxOffsetX = if (isLeftToRight) max else -max
    }

    internal suspend fun updateStateOnDragStopped() {
        isRevealed = offsetX.absoluteValue >= (maxOffsetX / 2f).absoluteValue
        animateDrag(if (isRevealed) maxOffsetX else 0f)
    }

    private suspend fun animateDrag(offset: Float) {
        animate(
            offsetX,
            offset,
            initialVelocity = REVEAL_VELOCITY,
            animationSpec = tween(TRANSITION_DURATION)
        ) { value, _ ->
            offsetX = value
        }
    }
}

private enum class SwipeToDismissLayout {
    CONTENT,
    REVEALABLE
}

enum class SwipeDirection {
    RTL,
    LTR
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DraggableLayoutPreview() {
    CapitalTheme {
        Column {
            DraggableLayoutPreview(SwipeDirection.LTR)
            DraggableLayoutPreview(SwipeDirection.RTL)
            DraggableLayoutPreview(SwipeDirection.LTR, revealed = true)
            DraggableLayoutPreview(SwipeDirection.RTL, revealed = true)
        }
    }
}

@Composable
private fun DraggableLayoutPreview(direction: SwipeDirection, revealed: Boolean = false) {
    val state = rememberSwipeToRevealLayoutState(direction = direction)
    SwipeToRevealLayout(
        state = state,
        revealContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(64.dp)
                    .background(color = CapitalTheme.colors.error)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(64.dp)
                    .background(color = CapitalTheme.colors.secondary)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = CapitalTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "DraggableLayout")
        }
    }
    LaunchedEffect(Unit) {
        if (revealed) {
            state.animateReveal()
        }
    }
}
