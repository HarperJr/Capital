package com.harper.capital.transaction

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.navigation.ScreenKey
import com.harper.capital.transaction.manage.TransactionManageParams
import com.harper.capital.transaction.manage.TransactionManageScreen
import com.harper.capital.transaction.manage.TransactionManageViewModel
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.navigation.NavArgsSpec
import com.harper.core.navigation.composable
import kotlinx.coroutines.launch
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
            defaultValue = TransactionType.LIABILITY.name
        }
    )

    override fun args(param: TransactionParams): Map<String, Any?> =
        mapOf(
            ACCOUNT_ID to param.accountId,
            TRANSACTION_TYPE to param.transactionType.name
        )
}

class TransactionParams(val accountId: Long?, val transactionType: TransactionType)

private const val TRANSACTION_PAGES_COUNT = 2
private const val TRANSACTION_PAGE_INDEX = 0
private const val TRANSACTION_PAGE_MANAGE_INDEX = 1

@OptIn(ExperimentalPagerApi::class)
@ExperimentalAnimationApi
fun NavGraphBuilder.transaction() {
    composable(
        route = ScreenKey.TRANSACTION.route,
        argsSpec = TransactionNavArgsSpec
    ) { (accountId: Long, type: String) ->
        val transactionViewModel = getViewModel<TransactionViewModel> {
            parametersOf(
                TransactionParams(
                    accountId = accountId.takeIf { it != -1L },
                    transactionType = TransactionType.valueOf(type)
                )
            )
        }
        val transactionManageViewModel = getViewModel<TransactionManageViewModel> {
            parametersOf(TransactionManageParams(mode = TransactionManageMode.ADD))
        }

        val coroutineScope = rememberCoroutineScope()
        var isNavigationScrollEnabled by remember { mutableStateOf(false) }
        val pagerState = rememberPagerState(initialPage = TRANSACTION_PAGE_INDEX)
        LaunchedEffect(Unit) {
            transactionViewModel.apply {
                onComposition()
            }
        }

        VerticalPager(
            count = TRANSACTION_PAGES_COUNT,
            state = pagerState,
            userScrollEnabled = isNavigationScrollEnabled
        ) { pageIndex ->
            when (pageIndex) {
                TRANSACTION_PAGE_INDEX -> TransactionScreen(viewModel = transactionViewModel) { source, receiver ->
                    isNavigationScrollEnabled = true
                    transactionManageViewModel.onEvent(TransactionManageEvent.Init(source, receiver))
                    coroutineScope.launch { pagerState.animateScrollToPage(TRANSACTION_PAGE_MANAGE_INDEX) }
                }
                TRANSACTION_PAGE_MANAGE_INDEX -> TransactionManageScreen(viewModel = transactionManageViewModel) {
                    coroutineScope.launch { pagerState.animateScrollToPage(TRANSACTION_PAGE_INDEX) }
                }
            }
        }
    }
}
