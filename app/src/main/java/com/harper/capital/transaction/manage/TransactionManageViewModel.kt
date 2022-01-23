package com.harper.capital.transaction.manage

import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.domain.FetchAssetUseCase
import com.harper.capital.transaction.manage.model.AssetPair
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class TransactionManageViewModel(
    private val params: TransactionManageFragment.Params,
    private val router: GlobalRouter,
    private val fetchAssetUseCase: FetchAssetUseCase
) : ComponentViewModel<TransactionManageState>(
    defaultState = TransactionManageState(transactionType = params.transactionType)
), EventObserver<TransactionManageEvent> {

    override fun onEvent(event: TransactionManageEvent) {
        when (event) {
            TransactionManageEvent.BackClick -> router.back()
        }
    }

    override fun onFirstStart() {
        super.onFirstStart()
        launch {
            val assetFrom = fetchAssetUseCase(params.assetFromId)
            val assetTo = fetchAssetUseCase(params.assetToId)
            mutateState {
                it.copy(assetPair = AssetPair(assetFrom, assetTo), isLoading = false)
            }
        }
    }
}
