package com.harper.capital.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import com.harper.capital.navigation.ScreenKey
import com.harper.core.component.transition.Transitions
import com.harper.core.component.transition.fadeTransition
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.settings() {
    composable(ScreenKey.SETTINGS.route, transitions = Transitions(fadeTransition())) {
        val viewModel = getViewModel<SettingsViewModel>()
        LaunchedEffect(Unit) {
            viewModel.apply {
                onComposition()
            }
        }
        SettingsScreen(viewModel = viewModel)
    }
}
