package com.harper.capital.history

import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.ChargeTransaction
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.LedgerType
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.history.domain.FetchTransactionsUseCase
import com.harper.capital.history.model.DatePickerDialogState
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListItem
import com.harper.capital.history.model.HistoryListState
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.TransactionManageFragment
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.core.ui.ComponentViewModelV1
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.time.LocalDate

@OptIn(FlowPreview::class)
class HistoryListViewModel(
    private val params: HistoryListFragment.Params,
    private val router: GlobalRouter,
    private val fetchTransactionsUseCase: FetchTransactionsUseCase
) : ComponentViewModelV1<HistoryListState, HistoryListEvent>(
    initialState = HistoryListState()
) {

    override fun onEvent(event: HistoryListEvent) {
        when (event) {
            HistoryListEvent.BackClick -> router.back()
            HistoryListEvent.FilterItemClick -> {
            }
            is HistoryListEvent.OnTransactionClick -> onTransactionClick(event)
            HistoryListEvent.PeriodSelectorClick -> onPeriodSelectorClick()
            HistoryListEvent.HideDialog -> onHideDialog()
            is HistoryListEvent.MonthSelect -> onMonthSelect(event)
        }
    }

    override fun onFirstComposition() {
        super.onFirstComposition()
        fetchTransactions()
    }

    private fun fetchTransactions() {
        launch {
            fetchTransactionsUseCase(params.assetId, state.value.selectedMonth)
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

    private fun onTransactionClick(event: HistoryListEvent.OnTransactionClick) {
        router.navigateToManageTransaction(
            TransactionManageFragment.Params(
                mode = TransactionManageMode.EDIT,
                transactionId = event.transaction.id,
                sourceAccountId = event.transaction.source.id,
                receiverAccountId = event.transaction.receiver.id
            )
        )
    }

    private fun dedicateLiabilities(transactions: List<Transaction>): Double {
        return transactions.filter { transaction ->
            transaction.ledgers.firstOrNull {
                it.type == LedgerType.CREDIT && it.account.type == AccountType.ASSET
            } != null
        }.sumOf { it.amount }
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
