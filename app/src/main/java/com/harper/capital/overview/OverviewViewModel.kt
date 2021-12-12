package com.harper.capital.overview

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Currency
import com.harper.capital.overview.domain.FetchAssetsUseCase
import com.harper.capital.overview.model.OverviewEvent
import com.harper.capital.overview.model.OverviewState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import com.harper.capital.navigation.GlobalRouter
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class OverviewViewModel(
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase
) : ComponentViewModel<OverviewState>(OverviewState.Loading),
    EventObserver<OverviewEvent> {

    override fun onFirstStart() {
        super.onFirstStart()

        launch {
            val assetsFlow = fetchAssetsUseCase()
            assetsFlow.collect { assets ->
                mutateState { OverviewState.Data(account = Account(12455.23, Currency.RUB), assets = assets) }
                Timber.d("Assets were received")
            }
        }
    }

    override fun onEvent(event: OverviewEvent) {
        when (event) {
            is OverviewEvent.AddAssetClick -> onAddAsset()
            is OverviewEvent.IncomeClick -> {}
            is OverviewEvent.ExpenseClick -> {}
        }
    }

    private fun onAddAsset() = router.navigateToAddAsset()
}
