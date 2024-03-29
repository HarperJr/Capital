package com.harper.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.harper.core.component.transition.Transitions
import com.harper.core.ext.orElse

@ExperimentalAnimationApi
fun NavGraphBuilder.composable(
    route: String,
    argsSpec: NavArgsSpec<*>? = null,
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavArgsHolder) -> Unit
) {
    val routeWithArgs = buildString {
        append(route)
        if (argsSpec != null && argsSpec.navArguments.isNotEmpty()) {
            append(
                argsSpec.navArguments.joinToString(prefix = "?", separator = "&") {
                    "${it.name}={${it.name}}"
                }
            )
        }
    }
    composable(
        route = routeWithArgs,
        arguments = argsSpec?.navArguments.orEmpty(),
        deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) { backStackEntry ->
        CompositionLocalProvider(LocalViewModelStoreOwner provides backStackEntry) {
            val parameters = backStackEntry.arguments
                ?.let { argsSpec?.getNavArgsHolder(it) }
                .orElse(NavArgsHolder())
            content.invoke(this, parameters)
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composable(
    route: String,
    argsSpec: NavArgsSpec<*>? = null,
    deepLinks: List<NavDeepLink> = emptyList(),
    transitions: Transitions = Transitions.NONE,
    content: @Composable AnimatedVisibilityScope.(NavArgsHolder) -> Unit
) = composable(
    route,
    argsSpec,
    deepLinks,
    enterTransition = transitions.enter,
    exitTransition = transitions.exit,
    popEnterTransition = transitions.popEnter,
    popExitTransition = transitions.popExit,
    content
)
