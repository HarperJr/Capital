package com.harper.capital.history

import com.harper.capital.domain.model.ChargeTransaction
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.history.domain.FetchTransactionsUseCase
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListItem
import com.harper.capital.history.model.HistoryListState
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.TransactionManageFragment
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.time.LocalDate

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
            is HistoryListEvent.OnTransactionClick -> onTransactionClick(event)
        }
    }

    override fun onFirstStart() {
        super.onFirstStart()
        launch {
            fetchTransactionsUseCase(params.assetId)
                .map { createHistoryListItems(it) }
                .collect { items ->
                    mutateState {
                        it.copy(items = items)
                    }
                }
        }
    }

    private fun onTransactionClick(event: HistoryListEvent.OnTransactionClick) {
        router.navigateToManageTransaction(
            TransactionManageFragment.Params(
                mode = TransactionManageMode.EDIT,
                transactionId = event.transactionId,
                sourceAccountId = 0L,
                receiverAccountId = 0L
            )
        )
    }

    private fun createHistoryListItems(transactions: List<Transaction>): List<HistoryListItem> {
        return mutableListOf<HistoryListItem>()
            .apply {
                val transactionsMap = mutableMapOf<LocalDate, MutableList<Transaction>>()
                transactions
                    .forEach {
                        transactionsMap.getOrPut(it.dateTime.toLocalDate()) { mutableListOf() }.add(it)
                    }
                transactionsMap.forEach { (date, transactions) ->
                    add(HistoryListItem.TransactionDateScopeItem(date = date, amount = transactions.sumOf { it.amount }, Currency.RUB))
                    transactions.forEach {
                        when (it) {
                            is ChargeTransaction -> add(HistoryListItem.ChargeTransactionItem(transaction = it))
                            is TransferTransaction -> add(HistoryListItem.TransferTransactionItem(transaction = it))
                        }
                    }
                }
            }
    }
}
