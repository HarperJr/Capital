package com.harper.capital.history

import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.LedgerType
import com.harper.capital.domain.model.Transaction
import com.harper.capital.history.domain.FetchTransactionsUseCase
import com.harper.capital.history.model.DatePickerDialogState
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListItem
import com.harper.capital.history.model.HistoryListState
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.TransactionManageParams
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.core.ext.orElse
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(FlowPreview::class)
class HistoryListViewModel(
    private val params: HistoryListParams,
    private val router: GlobalRouter,
    private val fetchTransactionsUseCase: FetchTransactionsUseCase
) : ComponentViewModel<HistoryListState, HistoryListEvent>(
    initialState = HistoryListState()
) {

    override fun onEvent(event: HistoryListEvent) {
        when (event) {
            is HistoryListEvent.BackClick -> router.back()
            is HistoryListEvent.FilterItemClick -> {
            }
            is HistoryListEvent.OnTransactionClick -> onTransactionClick(event)
            is HistoryListEvent.PeriodSelectorClick -> onPeriodSelectorClick()
            is HistoryListEvent.HideDialog -> onHideDialog()
            is HistoryListEvent.MonthSelect -> onMonthSelect(event)
        }
    }

    override fun onFirstComposition() {
        super.onFirstComposition()
        fetchTransactions()
    }

    private fun fetchTransactions() {
        launch {
            fetchTransactionsUseCase(params.accountId, state.value.selectedMonth)
                .map { createHistoryListItems(it) to dedicateLiabilities(it) }
                .collect { (transactionsItems, liabilities) ->
                    update {
                        it.copy(liabilities = liabilities, items = transactionsItems)
                    }
                }
        }
    }

    private fun onMonthSelect(event: HistoryListEvent.MonthSelect) {
        update {
            it.copy(
                selectedMonth = event.month,
                datePickerDialogState = it.datePickerDialogState.copy(date = event.month, isVisible = false)
            )
        }
        fetchTransactions()
    }

    private fun onHideDialog() {
        update {
            it.copy(datePickerDialogState = it.datePickerDialogState.copy(isVisible = false))
        }
    }

    private fun onPeriodSelectorClick() {
        update {
            it.copy(datePickerDialogState = DatePickerDialogState(it.selectedMonth, isVisible = true))
        }
    }

    // TODO fix this
    private fun onTransactionClick(event: HistoryListEvent.OnTransactionClick) {
        if (!event.transaction.isCharge) {
            router.navigateToManageTransaction(
                TransactionManageParams(
                    mode = TransactionManageMode.EDIT,
                    transactionId = event.transaction.id,
                    sourceAccountId = event.transaction.source.id,
                    receiverAccountId = event.transaction.ledgers[1].account.id
                )
            )
        }
    }

    private fun dedicateLiabilities(transactions: List<Transaction>): Double {
        return transactions.map { transaction ->
            transaction.ledgers.firstOrNull {
                it.type == LedgerType.CREDIT && it.account.type == AccountType.ASSET
            }?.amount.orElse(0.0)
        }.sum()
    }

    private fun createHistoryListItems(transactions: List<Transaction>): List<HistoryListItem> {
        val transactionsMap = mutableMapOf<LocalDate, MutableList<Transaction>>()
        transactions
            .forEach {
                transactionsMap.getOrPut(it.dateTime.toLocalDate()) { mutableListOf() }.add(it)
            }
        return transactionsMap.map { (date, transactions) ->
            HistoryListItem(date = date, summaryAmount = transactions.sumOf { it.source.amount }, Currency.RUB, transactions = transactions)
        }
    }
}
