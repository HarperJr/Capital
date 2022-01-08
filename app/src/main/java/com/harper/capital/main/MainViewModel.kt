package com.harper.capital.main

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Currency
import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import com.harper.capital.navigation.GlobalRouter
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
            is MainEvent.AddAssetClick -> onAddAsset()
            is MainEvent.IncomeClick -> {}
            is MainEvent.ExpenseClick -> {}
        }
    }

    private fun onAddAsset() = router.navigateToAddAsset()
}
