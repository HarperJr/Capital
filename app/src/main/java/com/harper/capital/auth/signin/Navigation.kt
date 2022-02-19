package com.harper.capital.auth.signin

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.harper.capital.navigation.ScreenKey
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.signIn() {
    composable(ScreenKey.SIGN_IN.name) { backStackEntry ->
        CompositionLocalProvider(LocalViewModelStoreOwner provides backStackEntry) {
            val viewModel = getViewModel<SignInViewModel>()
            LaunchedEffect(Unit) {
                viewModel.apply {
                    onComposition()
                }
            }
            SignInScreen(viewModel = viewModel)
        }
    }
}
