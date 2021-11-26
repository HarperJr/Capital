package com.harper.capital.overview.ui

import com.harper.capital.overview.domain.FetchAssetsUseCase
import com.harper.capital.overview.ui.model.OverviewEvent
import com.harper.capital.overview.ui.model.OverviewState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import com.harper.core.ui.navigation.GlobalRouter

class OverviewViewModel(
    private val router: GlobalRouter,
    private val fetchAssetsUseCase: FetchAssetsUseCase
) : ComponentViewModel<OverviewState>(OverviewState.Loading),
    EventObserver<OverviewEvent> {

    override fun onFirstStart() {
        super.onFirstStart()

        launch {
            val assets = fetchAssetsUseCase()
            mutateState { OverviewState.Data(assets = assets) }
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
