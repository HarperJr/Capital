package com.harper.core.component.transition

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry

@ExperimentalAnimationApi
fun verticalSlideTransition(
    duration: Int = TRANSITION_DURATION_MILLIS,
    delay: Int = 0,
    animationSpec: FiniteAnimationSpec<IntOffset> = tween(duration, delay)
): Transition = Transition(
    enter = { slideInVertically(animationSpec = animationSpec, initialOffsetY = { it }) },
    exit = { slideOutVertically(animationSpec = animationSpec, targetOffsetY = { it }) }
)
