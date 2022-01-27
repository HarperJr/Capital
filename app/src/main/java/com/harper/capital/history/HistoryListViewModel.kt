package com.harper.capital.history

import com.harper.capital.history.domain.FetchTransactionsUseCase
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListState
import com.harper.capital.navigation.GlobalRouter
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import kotlinx.coroutines.flow.collect

class HistoryListViewModel(
    private val params: HistoryListFragment.Params,
    private val router: GlobalRouter,
    private val fetchTransactionsUseCase: FetchTransactionsUseCase
) : ComponentViewModel<HistoryListState>(
    defaultState = HistoryListState()
), EventObserver<HistoryListEvent> {

    override fun onEvent(event: HistoryListEvent) {
        when (event) {
            HistoryListEvent.BackClick -> router.back()
            HistoryListEvent.FilterItemClick -> {
            }
        }
    }

    override fun onFirstStart() {
        super.onFirstStart()
        launch {
            fetchTransactionsUseCase(params.assetId)
                .collect { transactions ->
                    mutateState {
                        it.copy(transactions = transactions)
                    }
                }
        }
    }
}
