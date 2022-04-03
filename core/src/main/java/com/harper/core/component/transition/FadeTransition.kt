package com.harper.core.component.transition

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@ExperimentalAnimationApi
fun fadeTransition(
    duration: Int = TRANSITION_DURATION_MILLIS,
    delay: Int = 0,
    animationSpec: FiniteAnimationSpec<Float> = tween(duration, delay)
): Transition = Transition(
    enter = { fadeIn(animationSpec = animationSpec) },
    exit = { fadeOut(animationSpec = animationSpec) }
)
