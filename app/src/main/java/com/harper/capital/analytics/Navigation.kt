package com.harper.capital.analytics

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.harper.capital.analytics.model.AnalyticsType
import com.harper.capital.navigation.ScreenKey
import com.harper.core.component.transition.Transitions
import com.harper.core.component.transition.fadeTransition
import com.harper.core.component.transition.verticalSlideTransition
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object AnalyticsNavArgsSpec : NavArgsSpec<AnalyticsParams> {
    private const val TYPE = "type"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(TYPE) {
            type = NavType.StringType
            defaultValue = AnalyticsType.BALANCE.name
        }
    )

    override fun args(param: AnalyticsParams): Map<String, Any?> =
        mapOf(TYPE to param.analyticsType.name)
}

class AnalyticsParams(val analyticsType: AnalyticsType)

@ExperimentalAnimationApi
fun NavGraphBuilder.analytics() {
    composable(
        route = ScreenKey.ANALYTICS.route,
        argsSpec = AnalyticsNavArgsSpec,
        transitions = Transitions(enterExit = verticalSlideTransition() + fadeTransition())
    ) { (analyticsTypeName: String) ->
        val viewModel = getViewModel<AnalyticsViewModel> { parametersOf(AnalyticsType.valueOf(analyticsTypeName)) }
        LaunchedEffect(Unit) {
            viewModel.onComposition()
        }
        AnalyticsScreen(viewModel = viewModel)
    }
}
