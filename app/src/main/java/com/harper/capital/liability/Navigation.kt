package com.harper.capital.liability

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.harper.capital.liability.model.LiabilityManageType
import com.harper.capital.navigation.ScreenKey
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object LiabilityManageNavArgsSpec : NavArgsSpec<LiabilityManageParams> {
    private const val TYPE = "type"
    private const val ACCOUNT_ID = "account_ID"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(TYPE) {
            type = NavType.StringType
            defaultValue = LiabilityManageType.LIABILITY.name
        },
        navArgument(ACCOUNT_ID) {
            type = NavType.LongType
            defaultValue = -1L
        }
    )

    override fun args(param: LiabilityManageParams): Map<String, Any?> = mapOf(TYPE to param.type, ACCOUNT_ID to param.accountId)
}

class LiabilityManageParams(val type: LiabilityManageType, val accountId: Long? = null)

@ExperimentalAnimationApi
fun NavGraphBuilder.liabilityManage() {
    composable(
        route = ScreenKey.LIABILITY_MANAGE.route,
        argsSpec = LiabilityManageNavArgsSpec
    ) { (type: String, accountId: Long?) ->
        val viewModel = getViewModel<LiabilityManageViewModel> {
            parametersOf(LiabilityManageParams(LiabilityManageType.valueOf(type), accountId.takeIf { it != -1L }))
        }
        LaunchedEffect(Unit) {
            viewModel.apply {
                onComposition()
            }
        }
        LiabilityManageScreen(viewModel = viewModel)
    }
}
