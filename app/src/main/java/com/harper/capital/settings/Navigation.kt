package com.harper.capital.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.harper.capital.navigation.ScreenKey
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.settings() {
    composable(ScreenKey.SETTINGS.route) { backStackEntry ->
        CompositionLocalProvider(LocalViewModelStoreOwner provides backStackEntry) {
            val viewModel = getViewModel<SettingsViewModel>()
            LaunchedEffect(Unit) {
                viewModel.apply {
                    onComposition()
                }
            }
            SettingsScreen(viewModel = viewModel)
        }
    }
}
