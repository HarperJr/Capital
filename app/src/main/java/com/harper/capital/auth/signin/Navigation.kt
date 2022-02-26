package com.harper.capital.auth.signin

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import com.harper.capital.navigation.ScreenKey
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.signIn() {
    composable(ScreenKey.SIGN_IN.route) {
        val viewModel = getViewModel<SignInViewModel>()
        LaunchedEffect(Unit) {
            viewModel.apply {
                onComposition()
            }
        }
        SignInScreen(viewModel = viewModel)
    }
}
