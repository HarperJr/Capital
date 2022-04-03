package com.harper.capital.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.harper.capital.navigation.ScreenKey
import org.koin.androidx.compose.getViewModel


@ExperimentalAnimationApi
fun NavGraphBuilder.main() {
    composable(ScreenKey.MAIN.route) { backStackEntry ->
        CompositionLocalProvider(LocalViewModelStoreOwner provides backStackEntry) {
            val viewModel = getViewModel<MainViewModel>()
            LaunchedEffect(Unit) {
                viewModel.apply {
                    onComposition()
                }
            }
            MainScreen(viewModel = viewModel)
        }
    }
}
