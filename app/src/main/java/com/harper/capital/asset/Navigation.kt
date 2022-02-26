package com.harper.capital.asset

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.navigation.ScreenKey
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object AssetManageNavArgsSpec : NavArgsSpec<AssetManageParams> {
    private const val MODE = "mode"
    private const val ACCOUNT_ID = "account_id"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(MODE) {
            type = NavType.StringType
            defaultValue = AssetManageMode.ADD.name
        },
        navArgument(ACCOUNT_ID) {
            type = NavType.LongType
            defaultValue = -1L
        }
    )

    override fun args(param: AssetManageParams): Map<String, Any?> =
        mapOf(
            MODE to param.mode.name,
            ACCOUNT_ID to param.accountId
        )
}

class AssetManageParams(val mode: AssetManageMode, val accountId: Long? = null)

@ExperimentalAnimationApi
fun NavGraphBuilder.assetManage() {
    composable(
        route = ScreenKey.ASSET_MANAGE.route,
        argsSpec = AssetManageNavArgsSpec
    ) { (mode: String, accountId: Long) ->
        val viewModel = getViewModel<AssetManageViewModel> {
            parametersOf(
                AssetManageParams(
                    AssetManageMode.valueOf(mode),
                    accountId.takeIf { it != -1L }
                )
            )
        }
        LaunchedEffect(Unit) {
            viewModel.onComposition()
        }
        AssetManageScreen(viewModel = viewModel)
    }
}
