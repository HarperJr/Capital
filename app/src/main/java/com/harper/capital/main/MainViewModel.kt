package com.harper.capital.main

import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.asset.model.AssetManageMode
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Currency
import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.TransactionFragment
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class MainViewModel(
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase
) : ComponentViewModel<MainState>(MainState.Loading),
    EventObserver<MainEvent> {

    override fun onFirstStart() {
        super.onFirstStart()

        launch {
            val assetsFlow = fetchAssetsUseCase()
            assetsFlow.collect { assets ->
                mutateState { MainState.Data(account = Account(12455.23, Currency.RUB), assets = assets) }
                Timber.d("Assets were received")
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
        }
    }

    private fun onNewAssetClick() {
        router.navigateToManageAsset(AssetManageFragment.Params(AssetManageMode.ADD))
    }

    private fun onHistoryClick(event: MainEvent.HistoryClick) {

    }

    private fun onIncomeClick(event: MainEvent.IncomeClick) {
        router.navigateToTransaction(TransactionFragment.Params(assetId = event.asset?.id, TransactionType.INCOME))
    }

    private fun onExpenseClick(event: MainEvent.ExpenseClick) {
        router.navigateToTransaction(TransactionFragment.Params(assetId = event.asset?.id, TransactionType.EXPENSE))
    }

    private fun onEditClick(event: MainEvent.EditClick) {
        router.navigateToManageAsset(AssetManageFragment.Params(AssetManageMode.EDIT, assetId = event.asset.id))
    }
}
