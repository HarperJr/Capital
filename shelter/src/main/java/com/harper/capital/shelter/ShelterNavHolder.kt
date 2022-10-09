package com.harper.capital.shelter

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.harper.capital.shelter.core.ScreenLayout
import com.harper.capital.shelter.details.DetailsScreen
import com.harper.capital.shelter.details.ShelterDetailsViewModel
import com.harper.capital.shelter.main.MainScreen
import com.harper.capital.shelter.main.ShelterMainViewModel
import com.harper.capital.shelter.model.ShelterDetailParams
import com.harper.capital.shelter.v2.ui.ExampleScreen
import com.harper.capital.shelter.v2.ui.ExampleViewModel
import com.harper.capital.shelter.v2.ui.model.ExampleEvent
import com.harper.capital.shelter.v2.ui.model.ExampleRouteEvent
import com.harper.core.ext.orElse
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShelterNavHolder() {
    val controller = rememberAnimatedNavController()
    val config = LocalConfiguration.current
    ScreenLayout {
        AnimatedNavHost(navController = controller, startDestination = ShelterScreens.MAIN.name) {
            composable(route = ShelterScreens.MAIN.name) {
                MainScreen(viewModel = getViewModel<ShelterMainViewModel>(), onDetailsClick = { name ->
                    controller.navigate("${ShelterScreens.DETAILS.name}/$name")
                }, onExampleClick = { controller.navigate(ShelterScreens.EXAMPLE.name) })
            }
            composable(
                route = "${ShelterScreens.DETAILS.name}/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType }),
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { config.screenWidthDp }, animationSpec = tween(600)) +
                            fadeIn(animationSpec = tween(1000))
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { config.screenWidthDp }, animationSpec = tween(600)) +
                            fadeOut(animationSpec = tween(1000))
                }
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name").orElse("No title")
                DetailsScreen(
                    viewModel = getViewModel<ShelterDetailsViewModel>() {
                        parametersOf(ShelterDetailParams(name))
                    },
                    onBackClick = {
                        controller.popBackStack()
                    })
            }
            composable(route = ShelterScreens.EXAMPLE.name) {
                val viewModel = getViewModel<ExampleViewModel>()
                BackHandler { viewModel.postEvent(ExampleEvent.BackClick) }

                LaunchedEffect(viewModel) {
                    viewModel.routeEvents
                        .collect { event ->
                            when (event) {
                                ExampleRouteEvent.Back -> controller.navigateUp()
                            }
                        }
                }

                ExampleScreen(viewModel = viewModel)
            }
        }
    }
}

