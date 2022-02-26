package com.harper.capital.transaction.manage

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.harper.capital.navigation.ScreenKey
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object TransactionManageNavArgsSpec : NavArgsSpec<TransactionManageParams> {
    private const val TRANSACTION_ID = "transaction_id"
    private const val SOURCE_ACCOUNT_ID = "source_id"
    private const val RECEIVER_ACCOUNT_ID = "receiver_id"
    private const val MODE = "mode"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(TRANSACTION_ID) {
            type = NavType.LongType
            defaultValue = -1L
        },
        navArgument(SOURCE_ACCOUNT_ID) {
            type = NavType.LongType
            defaultValue = -1L
        },
        navArgument(RECEIVER_ACCOUNT_ID) {
            type = NavType.LongType
            defaultValue = -1L
        },
        navArgument(MODE) {
            type = NavType.StringType
            defaultValue = TransactionManageMode.ADD.name
        }
    )

    override fun args(param: TransactionManageParams): Map<String, Any?> =
        mapOf(
            TRANSACTION_ID to param.transactionId,
            SOURCE_ACCOUNT_ID to param.sourceAccountId,
            RECEIVER_ACCOUNT_ID to param.receiverAccountId,
            MODE to param.mode
        )
}

class TransactionManageParams(
    val mode: TransactionManageMode,
    val transactionId: Long? = null,
    val sourceAccountId: Long,
    val receiverAccountId: Long
)

@ExperimentalAnimationApi
fun NavGraphBuilder.transactionManage() {
    composable(
        route = ScreenKey.TRANSACTION_MANAGE.route,
        argsSpec = TransactionManageNavArgsSpec,
        enterTransition = { slideInVertically { it } + fadeIn() },
        exitTransition = { slideOutVertically { it } + fadeOut() }
    ) { (transactionId: Long, sourceId: Long, receiverId: Long, mode: String) ->
        val viewModel = getViewModel<TransactionManageViewModel> {
            parametersOf(
                TransactionManageParams(
                    transactionId = transactionId.takeIf { it != -1L },
                    sourceAccountId = sourceId,
                    receiverAccountId = receiverId,
                    mode = TransactionManageMode.valueOf(mode)
                )
            )
        }
        LaunchedEffect(Unit) {
            viewModel.apply {
                onComposition()
            }
        }
        TransactionManageScreen(viewModel = viewModel)
    }
}
