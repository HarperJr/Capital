package com.harper.core.component.transition

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry
import com.harper.core.ext.orElse

const val TRANSITION_DURATION_MILLIS = 300

@ExperimentalAnimationApi
class Transition(
    val enter: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = { EnterTransition.None },
    val exit: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = { ExitTransition.None }
) {

    operator fun plus(transition: Transition): Transition =
        Transition(
            enter = { enter.invoke(this) + transition.enter.invoke(this) },
            exit = { exit.invoke(this) + transition.exit.invoke(this) }
        )

    companion object {

        fun conditional(transition: Transition, condition: (NavBackStackEntry) -> Boolean): Transition {
            return Transition(
                enter = {
                    transition.enter.takeIf { condition.invoke(this.targetState) }
                        ?.invoke(this).orElse(EnterTransition.None)
                },
                exit = {
                    transition.exit.takeIf { condition.invoke(this.targetState) }
                        ?.invoke(this).orElse(ExitTransition.None)
                },
            )
        }
    }
}

@ExperimentalAnimationApi
class Transitions(
    private val enterExit: Transition = Transition(),
    private val popEnterExit: Transition = Transition()
) {
    val enter: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition get() = enterExit.enter
    val exit: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition get() = enterExit.exit
    val popEnter: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition get() = popEnterExit.enter
    val popExit: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition get() = popEnterExit.exit

    companion object {

        val NONE: Transitions = Transitions()
    }
}
