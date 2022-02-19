package com.harper.capital.main

import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.history.HistoryListFragment
import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.main.domain.FetchSummaryUseCase
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.TransactionFragment
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModelV1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class MainViewModel(
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase,
    private val fetchSummaryUseCase: FetchSummaryUseCase
) : ComponentViewModelV1<MainState, MainEvent>(initialState = MainState()) {

    override fun onFirstComposition() {
        super.onFirstComposition()
        fetchAssets()
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

    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.NewAssetClick -> onNewAssetClick()
            is MainEvent.HistoryClick -> onHistoryClick(event)
            is MainEvent.IncomeClick -> onIncomeClick(event)
            is MainEvent.ExpenseClick -> onExpenseClick(event)
            is MainEvent.EditClick -> onEditClick(event)
            MainEvent.SettingsClick -> onSettingsClick()
        }
    }

    private fun onSettingsClick() {
        router.navigateToSettings()
    }

    private fun onNewAssetClick() {
        router.navigateToManageAsset(AssetManageFragment.Params(AssetManageMode.ADD))
    }

    private fun onHistoryClick(event: MainEvent.HistoryClick) {
        router.navigateToHistoryList(HistoryListFragment.Params(assetId = event.account?.id))
    }

    private fun onIncomeClick(event: MainEvent.IncomeClick) {
        router.navigateToTransaction(
            TransactionFragment.Params(
                assetId = event.account?.id,
                TransactionType.INCOME
            )
        )
    }

    private fun onExpenseClick(event: MainEvent.ExpenseClick) {
        router.navigateToTransaction(
            TransactionFragment.Params(
                assetId = event.account?.id,
                TransactionType.EXPENSE
            )
        )
    }

    private fun onEditClick(event: MainEvent.EditClick) {
        router.navigateToManageAsset(
            AssetManageFragment.Params(
                AssetManageMode.EDIT,
                accountId = event.account.id
            )
        )
    }
}
