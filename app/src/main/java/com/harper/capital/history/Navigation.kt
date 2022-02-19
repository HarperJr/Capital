package com.harper.capital.history

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.harper.capital.navigation.ScreenKey
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object HistoryListManageNavArgsSpec : NavArgsSpec<HistoryListParams> {
    private const val ACCOUNT_ID = "account_id"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(ACCOUNT_ID) {
            type = NavType.LongType
            defaultValue = -1L
        }
    )

    override fun getArguments(param: HistoryListParams): Map<String, Any?> =
        mapOf(ACCOUNT_ID to param.accountId)
}

class HistoryListParams(val accountId: Long?)

@ExperimentalAnimationApi
fun NavGraphBuilder.historyList() {
    composable(
        route = ScreenKey.HISTORY_LIST.route,
        argsSpec = HistoryListManageNavArgsSpec
    ) { (accountId: Long) ->
        val viewModel = getViewModel<HistoryListViewModel> {
            parametersOf(
                HistoryListParams(accountId = accountId.takeIf { it != -1L })
            )
        }
        LaunchedEffect(Unit) {
            viewModel.apply {
                onComposition()
            }
        }
        HistoryListScreen(viewModel = viewModel)
    }
}
