package com.harper.capital.main

import com.harper.capital.analytics.AnalyticsParams
import com.harper.capital.analytics.model.AnalyticsType
import com.harper.capital.asset.AssetManageParams
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.history.HistoryListParams
import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.main.domain.FetchFavoriteTransactionsUseCase
import com.harper.capital.main.domain.FetchSummaryUseCase
import com.harper.capital.main.domain.UpdateCurrenciesUseCase
import com.harper.capital.main.model.ActionCardType
import com.harper.capital.main.model.MainBottomSheet
import com.harper.capital.main.model.MainBottomSheetState
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.TransactionParams
import com.harper.capital.transaction.manage.TransactionManageParams
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ext.orElse
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase,
    private val fetchSummaryUseCase: FetchSummaryUseCase,
    private val fetchFavoriteTransactionsUseCase: FetchFavoriteTransactionsUseCase,
    private val updateCurrenciesUseCase: UpdateCurrenciesUseCase
) : ComponentViewModel<MainState, MainEvent>(initialState = MainState()) {

    override fun onFirstComposition() {
        super.onFirstComposition()
        refreshCurrencies()
        fetchFavoriteTransactions()
        fetchAssets()
    }

    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.NewAssetClick -> onNewAssetClick()
            is MainEvent.HistoryClick -> onHistoryClick(event)
            is MainEvent.IncomeClick -> onIncomeClick(event)
            is MainEvent.ExpenseClick -> onExpenseClick(event)
            is MainEvent.EditClick -> onEditClick(event)
            is MainEvent.SettingsClick -> onSettingsClick()
            is MainEvent.ActionCardClick -> onActionCardClick(event)
            is MainEvent.AllOperationsClick -> onAllOperationsClick()
            is MainEvent.FavoriteTransferTransactionClick -> onFavoriteTransferTransactionClick(event)
        }
    }

    private fun onFavoriteTransferTransactionClick(event: MainEvent.FavoriteTransferTransactionClick) {
        router.navigateToManageTransaction(
            TransactionManageParams(
                mode = TransactionManageMode.ADD,
                sourceAccountId = event.transaction.source.id,
                receiverAccountId = event.transaction.receiver.id
            )
        )
    }

    private fun onAllOperationsClick() {
        router.navigateToHistoryList(HistoryListParams(accountId = null))
        return
    }

    private fun refreshCurrencies() {
        launch {
            updateCurrenciesUseCase()
        }
    }

    private fun fetchFavoriteTransactions() {
        launch {
            fetchFavoriteTransactionsUseCase()
                .filter { it.isNotEmpty() }
                .flowOn(Dispatchers.Main)
                .collect { transactions ->
                    update {
                        it.copy(bottomSheetState = MainBottomSheetState(bottomSheet = MainBottomSheet(transactions)))
                    }
                }
        }
    }

    private fun fetchAssets() {
        launch {
            combine(
                fetchAssetsUseCase(),
                fetchSummaryUseCase()
            ) { assets, summary -> assets to summary }
                .flowOn(Dispatchers.Main)
                .collect { (assets, summary) ->
                    update {
                        it.copy(
                            accounts = assets,
                            summary = summary,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun onActionCardClick(event: MainEvent.ActionCardClick) {
        val actionCardType = ActionCardType.of(event.id)
        getAnalyticsType(actionCardType)
            ?.let { router.navigateToAnalytics(AnalyticsParams(it)) }
            .orElse {
                if (actionCardType == ActionCardType.ACCOUNTS) {
                    router.navigateToAccounts()
                    return
                }
            }
    }

    private fun onSettingsClick() {
        router.navigateToSettings()
    }

    private fun onNewAssetClick() {
        router.navigateToManageAsset(AssetManageParams(AssetManageMode.ADD))
    }

    private fun onHistoryClick(event: MainEvent.HistoryClick) {
        router.navigateToHistoryList(HistoryListParams(accountId = event.account?.id))
    }

    private fun onIncomeClick(event: MainEvent.IncomeClick) {
        router.navigateToTransaction(
            TransactionParams(
                accountId = event.account?.id,
                TransactionType.INCOME
            )
        )
    }

    private fun onExpenseClick(event: MainEvent.ExpenseClick) {
        router.navigateToTransaction(
            TransactionParams(
                accountId = event.account?.id,
                TransactionType.EXPENSE
            )
        )
    }

    private fun onEditClick(event: MainEvent.EditClick) {
        router.navigateToManageAsset(
            AssetManageParams(
                AssetManageMode.EDIT,
                accountId = event.account.id
            )
        )
    }

    private fun getAnalyticsType(actionCardType: ActionCardType): AnalyticsType? = when (actionCardType) {
        ActionCardType.ANALYTICS_BALANCE -> AnalyticsType.BALANCE
        ActionCardType.ANALYTICS_INCOME -> AnalyticsType.INCOME
        ActionCardType.ANALYTICS_INCOME_LIABILITY -> AnalyticsType.INCOME_LIABILITY
        ActionCardType.ANALYTICS_LIABILITY -> AnalyticsType.LIABILITY
        else -> null
    }
}
