package com.harper.capital.category

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.harper.capital.category.model.CategoryManageType
import com.harper.capital.navigation.ScreenKey
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object CategoryManageNavArgsSpec : NavArgsSpec<CategoryManageParams> {
    private const val TYPE = "type"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(TYPE) {
            type = NavType.StringType
            defaultValue = CategoryManageType.LIABILITY.name
        }
    )

    override fun getArguments(param: CategoryManageParams): Map<String, Any?> = mapOf(TYPE to param.type)
}

class CategoryManageParams(val type: CategoryManageType)

@ExperimentalAnimationApi
fun NavGraphBuilder.categoryManage() {
    composable(
        route = ScreenKey.CATEGORY_MANAGE.route,
        argsSpec = CategoryManageNavArgsSpec
    ) { (type: String) ->
        val viewModel = getViewModel<CategoryManageViewModel> {
            parametersOf(
                CategoryManageParams(CategoryManageType.valueOf(type))
            )
        }
        LaunchedEffect(Unit) {
            viewModel.apply {
                onComposition()
            }
        }
        CategoryManageScreen(viewModel = viewModel)
    }
}
