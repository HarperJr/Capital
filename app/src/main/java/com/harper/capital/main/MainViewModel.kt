package com.harper.capital.main

import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.domain.model.TransactionType
import com.harper.capital.history.HistoryListFragment
import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.main.domain.FetchSummaryUseCase
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.TransactionFragment
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase,
    private val fetchSummaryUseCase: FetchSummaryUseCase
) : ComponentViewModel<MainState>(defaultState = MainState()),
    EventObserver<MainEvent> {

    override fun onFirstStart() {
        super.onFirstStart()

        launch {
            fetchAssetsUseCase()
                .collect { assets ->
                    mutateState {
                        it.copy(
                            assets = assets,
                            isLoading = false
                        )
                    }
                }
        }
        launch {
            fetchSummaryUseCase()
                .collect { summary ->
                    mutateState {
                        it.copy(summary = summary)
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
        router.navigateToHistoryList(HistoryListFragment.Params(assetId = event.asset?.id))
    }

    private fun onIncomeClick(event: MainEvent.IncomeClick) {
        router.navigateToTransaction(
            TransactionFragment.Params(
                assetId = event.asset?.id,
                TransactionType.INCOME
            )
        )
    }

    private fun onExpenseClick(event: MainEvent.ExpenseClick) {
        router.navigateToTransaction(
            TransactionFragment.Params(
                assetId = event.asset?.id,
                TransactionType.EXPENSE
            )
        )
    }

    private fun onEditClick(event: MainEvent.EditClick) {
        router.navigateToManageAsset(
            AssetManageFragment.Params(
                AssetManageMode.EDIT,
                assetId = event.asset.id
            )
        )
    }
}
