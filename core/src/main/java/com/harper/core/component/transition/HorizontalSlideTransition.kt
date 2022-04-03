package com.harper.core.component.transition

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.IntOffset

@ExperimentalAnimationApi
fun horizontalSlideTransition(
    duration: Int = TRANSITION_DURATION_MILLIS,
    delay: Int = 0,
    animationSpec: FiniteAnimationSpec<IntOffset> = tween(duration, delay)

): Transition = Transition(
    enter = { slideInHorizontally(animationSpec = animationSpec, initialOffsetX = { it }) },
    exit = { slideOutHorizontally(animationSpec = animationSpec, targetOffsetX = { it }) }
)
