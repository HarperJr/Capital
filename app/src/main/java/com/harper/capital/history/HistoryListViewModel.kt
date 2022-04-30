package com.harper.capital.history

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.ChangeTransaction
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.CurrencyRate
import com.harper.capital.domain.model.LedgerType
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.ext.getExchangeRate
import com.harper.capital.history.domain.FetchTransactionsUseCase
import com.harper.capital.history.model.DatePickerDialogState
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListItem
import com.harper.capital.history.model.HistoryListState
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.settings.domain.GetSettingsUseCase
import com.harper.capital.transaction.manage.TransactionManageParams
import com.harper.capital.transaction.manage.domain.FetchAccountUseCase
import com.harper.capital.transaction.manage.domain.FetchCurrencyRatesUseCase
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.core.ext.lazyAsync
import com.harper.core.ext.orElse
import com.harper.core.ui.ComponentViewModel
import java.time.LocalDate
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class HistoryListViewModel(
    private val params: HistoryListParams,
    private val router: GlobalRouter,
    private val fetchTransactionsUseCase: FetchTransactionsUseCase,
    private val fetchCurrencyRatesUseCase: FetchCurrencyRatesUseCase,
    private val fetchAccountUseCase: FetchAccountUseCase,
    private val settingsUseCase: GetSettingsUseCase
) : ComponentViewModel<HistoryListState, HistoryListEvent>(
    initialState = HistoryListState()
) {
    private val lazySettings = lazyAsync { settingsUseCase() }
    private val lazyRates = lazyAsync { fetchCurrencyRatesUseCase() }

    override fun onEvent(event: HistoryListEvent) {
        when (event) {
            is HistoryListEvent.BackClick -> router.back()
            is HistoryListEvent.FilterItemClick -> {}
            is HistoryListEvent.OnTransactionClick -> onTransactionClick(event)
            is HistoryListEvent.PeriodSelectorClick -> onPeriodSelectorClick()
            is HistoryListEvent.HideDialog -> onHideDialog()
            is HistoryListEvent.DateRangeSelect -> onDateRangeSelect(event)
        }
    }

    override fun onFirstComposition() {
        super.onFirstComposition()
        fetchTransactions()
    }

    private fun fetchTransactions() {
        launch {
            val settings = settingsUseCase()
            val account = params.accountId?.let { fetchAccountUseCase(it) }
            fetchTransactionsUseCase(account?.id, state.value.dateStart, state.value.dateEnd)
                .map { createHistoryListItems(it, account) to dedicateLiabilities(it) }
                .collect { (transactionsItems, liabilities) ->
                    update {
                        it.copy(currency = account?.currency ?: settings.currency, liabilities = liabilities, items = transactionsItems)
                    }
                }
        }
    }

    private fun onDateRangeSelect(event: HistoryListEvent.DateRangeSelect) {
        var dateStart = LocalDate.now()
        var dateEnd = LocalDate.now()
        if (event.dateStart != null && event.dateEnd != null) {
            dateStart = event.dateStart
            dateEnd = event.dateEnd
        }
        update {
            it.copy(
                dateStart = dateStart,
                dateEnd = dateEnd,
                datePickerDialogState = it.datePickerDialogState.copy(dateStart = dateStart, dateEnd = dateEnd, isVisible = false)
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
            it.copy(datePickerDialogState = DatePickerDialogState(dateStart = it.dateStart, dateEnd = it.dateEnd, isVisible = true))
        }
    }

    private fun onTransactionClick(event: HistoryListEvent.OnTransactionClick) {
        val transaction = event.transaction
        if (transaction is TransferTransaction) {
            router.navigateToManageTransaction(
                TransactionManageParams(
                    mode = TransactionManageMode.EDIT,
                    transactionId = transaction.id,
                    sourceAccountId = transaction.source.id,
                    receiverAccountId = transaction.receiver.id
                )
            )
        }
    }

    private suspend fun dedicateLiabilities(transactions: List<Transaction>): Double {
        val settings = lazySettings.await()
        val rates = lazyRates.await()
        return transactions.map { transaction ->
            transaction.ledgers
                .firstOrNull { it.type == LedgerType.CREDIT && it.account.type == AccountType.ASSET }
                ?.let { exchangeIfNeeded(rates, it.account.currency, settings.currency, it.amount) }
                .orElse(0.0)
        }.sum()
    }

    private suspend fun createHistoryListItems(transactions: List<Transaction>, account: Account?): List<HistoryListItem> {
        val settings = lazySettings.await()
        val rates = lazyRates.await()
        val transactionsMap = transactions.groupBy { it.dateTime.toLocalDate() }
        return transactionsMap.map { (date, transactions) ->
            val summaryAmount = transactions.sumOf {
                when (it) {
                    is TransferTransaction -> {
                        val amount = exchangeIfNeeded(rates, it.source.currency, settings.currency, it.sourceAmount)
                        if (it.source.id == account?.id) -amount else amount
                    }
                    is ChangeTransaction -> exchangeIfNeeded(rates, it.account.currency, settings.currency, it.amount)
                    else -> 0.0
                }
            }
            val summaryCurrency = account?.currency ?: settings.currency
            HistoryListItem(date = date, summaryAmount = summaryAmount, summaryCurrency, transactions = transactions)
        }
    }

    private fun exchangeIfNeeded(rates: List<CurrencyRate>, currency: Currency, baseCurrency: Currency, amount: Double): Double {
        return if (params.accountId == null) amount * baseCurrency.getExchangeRate(rates, currency) else amount
    }
}
