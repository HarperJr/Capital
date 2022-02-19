package com.harper.capital.asset

import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.navigation.ScreenKey
import com.harper.capital.navigation.v1.NavArgsHolder
import com.harper.core.ext.orElse
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object AssetManageNavArgsHolder : NavArgsHolder<AssetManageFragment.Params> {
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

    override fun getArguments(param: AssetManageFragment.Params): Map<String, Any?> =
        mapOf(
            MODE to param.mode,
            ACCOUNT_ID to param.accountId
        )

    override fun getData(args: Bundle): AssetManageFragment.Params =
        AssetManageFragment.Params(
            mode = args.getString(MODE)?.let(AssetManageMode::valueOf).orElse(AssetManageMode.ADD),
            accountId = args.getLong(ACCOUNT_ID, -1L).takeIf { it != -1L }
        )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.assetManage() {
    composable(
        route = "${ScreenKey.ASSET_MANAGE.name}?mode={mode}&account_id={account_id}",
        arguments = AssetManageNavArgsHolder.navArguments
    ) { backStackEntry ->
        CompositionLocalProvider(LocalViewModelStoreOwner provides backStackEntry) {
            val parameters = backStackEntry.arguments?.let {
                AssetManageNavArgsHolder.getData(it)
            }.orElse(AssetManageFragment.Params(mode = AssetManageMode.ADD))

            val viewModel = getViewModel<AssetManageViewModel> { parametersOf(parameters) }
            LaunchedEffect(Unit) {
                viewModel.apply {
                    onComposition()
                }
            }
            AssetManageScreen(viewModel = viewModel)
        }
    }
}
