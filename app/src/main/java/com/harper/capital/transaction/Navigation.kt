package com.harper.capital.transaction

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.harper.capital.navigation.ScreenKey
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object TransactionNavArgsSpec : NavArgsSpec<TransactionParams> {
    private const val ACCOUNT_ID = "account_id"
    private const val TRANSACTION_TYPE = "transaction_type"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(ACCOUNT_ID) {
            type = NavType.LongType
            defaultValue = -1L
        },
        navArgument(TRANSACTION_TYPE) {
            type = NavType.StringType
            defaultValue = TransactionType.EXPENSE.name
        }
    )

    override fun getArguments(param: TransactionParams): Map<String, Any?> =
        mapOf(
            ACCOUNT_ID to param.accountId,
            TRANSACTION_TYPE to param.transactionType.name
        )
}

class TransactionParams(val accountId: Long?, val transactionType: TransactionType)

@ExperimentalAnimationApi
fun NavGraphBuilder.transaction() {
    composable(
        route = ScreenKey.TRANSACTION.route,
        argsSpec = TransactionNavArgsSpec
    ) { (accountId: Long, type: String) ->
        val viewModel = getViewModel<TransactionViewModel> {
            parametersOf(
                TransactionParams(
                    accountId = accountId.takeIf { it != -1L },
                    transactionType = TransactionType.valueOf(type)
                )
            )
        }
        LaunchedEffect(Unit) {
            viewModel.apply {
                onComposition()
            }
        }
        TransactionScreen(viewModel = viewModel)
    }
}
