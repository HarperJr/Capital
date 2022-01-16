package com.harper.capital.transaction

import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class TransactionViewModel(
    params: TransactionFragment.Params,
    private val router: GlobalRouter
) : ComponentViewModel<TransactionState>(
    defaultState = TransactionState(transactionType = params.transactionType)
), EventObserver<TransactionEvent> {

    override fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.BackClick -> router.back()
            is TransactionEvent.OnTabSelect -> onTabSelect(event)
        }
    }

    private fun onTabSelect(event: TransactionEvent.OnTabSelect) {
        mutateState {
            it.copy(transactionType = TransactionType.of(event.tabIndex))
        }
    }
}
