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
import com.harper.capital.settings.domain.FetchSettingsUseCase
import com.harper.capital.transaction.TransactionParams
import com.harper.capital.transaction.manage.TransactionManageParams
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class MainViewModel(
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase,
    private val fetchSummaryUseCase: FetchSummaryUseCase,
    private val fetchSettingsUseCase: FetchSettingsUseCase,
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
                fetchSummaryUseCase(),
                fetchSettingsUseCase()
            ) { assets, summary, settings -> Triple(assets, summary, settings) }
                .collect { (assets, summary, settings) ->
                    update {
                        it.copy(
                            accounts = assets,
                            accountPresentation = settings.accountPresentation,
                            summary = summary,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun onActionCardClick(event: MainEvent.ActionCardClick) {
        when (event.type) {
            ActionCardType.ACCOUNTS -> router.navigateToAccounts()
            ActionCardType.ANALYTICS -> router.navigateToAnalytics(AnalyticsParams(AnalyticsType.BALANCE))
            ActionCardType.FAVORITE -> {}
            ActionCardType.PLAN -> {}
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
                TransactionType.LIABILITY
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
}
