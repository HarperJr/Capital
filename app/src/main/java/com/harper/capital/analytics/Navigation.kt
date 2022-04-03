package com.harper.capital.analytics

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.harper.capital.navigation.ScreenKey
import com.harper.core.component.transition.Transitions
import com.harper.core.component.transition.fadeTransition
import com.harper.core.component.transition.verticalSlideTransition
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object AnalyticsNavArgsSpec : NavArgsSpec<AnalyticsParams> {
    private const val SECTION_ID = "section_id"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(SECTION_ID) {
            type = NavType.IntType
            defaultValue = -1
        }
    )

    override fun args(param: AnalyticsParams): Map<String, Any?> =
        mapOf(SECTION_ID to param.sectionId)
}

class AnalyticsParams(val sectionId: Int)

@ExperimentalAnimationApi
fun NavGraphBuilder.analytics() {
    composable(
        route = ScreenKey.ANALYTICS.route,
        argsSpec = AnalyticsNavArgsSpec,
        transitions = Transitions(enterExit = verticalSlideTransition() + fadeTransition())
    ) { (sectionId: Int) ->
        val viewModel = getViewModel<AnalyticsViewModel> { parametersOf(sectionId) }
        LaunchedEffect(Unit) {
            viewModel.onComposition()
        }
        AnalyticsScreen(viewModel = viewModel)
    }
}
