package com.harper.capital.transaction.manage

import com.harper.capital.domain.model.*
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.domain.*
import com.harper.capital.transaction.manage.model.ExchangeState
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime

class TransactionManageViewModel(
    private val params: TransactionManageParams,
    private val router: GlobalRouter,
    private val fetchAssetUseCase: FetchAssetUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val fetchTransactionUseCase: FetchTransactionUseCase,
    private val fetchCurrencyRatesUseCase: FetchCurrencyRatesUseCase
) : ComponentViewModel<TransactionManageState, TransactionManageEvent>(
    initialState = TransactionManageState(mode = params.mode)
) {
    override fun onEvent(event: TransactionManageEvent) {
        when (event) {
            is TransactionManageEvent.BackClick -> router.back()
            is TransactionManageEvent.SourceAmountChange -> onSourceAmountChange(event)
            is TransactionManageEvent.TargetAmountChange -> onTargetAmountChange(event)
            is TransactionManageEvent.CommentChange -> onCommentChange(event)
            is TransactionManageEvent.DateSelect -> onDateSelect(event)
            is TransactionManageEvent.ScheduledCheckChange -> onScheduledCheckChange(event)
            is TransactionManageEvent.Apply -> onApply()
        }
    }

    override fun onFirstComposition() {
        super.onFirstComposition()
        if (params.transactionId == null) {
            fetchAccounts()
        } else {
            fetchTransaction(params.transactionId)
        }
    }

    private fun fetchTransaction(transactionId: Long) {
        launch {
            val transaction = fetchTransactionUseCase(transactionId)
            val rates = fetchCurrencyRatesUseCase()
            val sourceAccount = transaction.ledgers[0].account
            val receiverAccount = transaction.ledgers[1].account
            val rate = getExchangeRate(rates, sourceAccount.currency, receiverAccount.currency)
            update {
                it.copy(
                    accounts = transaction.ledgers.map { ledger -> ledger.account },
                    exchangeState = ExchangeState(
                        sourceCurrency = sourceAccount.currency,
                        receiverCurrency = receiverAccount.currency,
                        rate = rate
                    ),
                    date = transaction.dateTime,
                    comment = transaction.comment,
                    isScheduled = transaction.isScheduled,
                    isLoading = false
                )
            }
        }
    }

    private fun fetchAccounts() {
        launch {
            val sourceAccount = fetchAssetUseCase(params.sourceAccountId)
            val receiverAccount = fetchAssetUseCase(params.receiverAccountId)
            val rates = fetchCurrencyRatesUseCase()
            val rate = getExchangeRate(rates, sourceAccount.currency, receiverAccount.currency)
            update {
                it.copy(
                    accounts = listOf(sourceAccount, receiverAccount),
                    exchangeState = ExchangeState(
                        sourceCurrency = sourceAccount.currency,
                        receiverCurrency = receiverAccount.currency,
                        rate = rate
                    ),
                    isLoading = false
                )
            }
        }
    }

    private fun onSourceAmountChange(event: TransactionManageEvent.SourceAmountChange) {
        update {
            it.copy(
                exchangeState = it.exchangeState.copy(
                    sourceAmount = event.amount,
                    receiverAmount = event.amount / it.exchangeState.rate,
                )
            )
        }
    }

    private fun onTargetAmountChange(event: TransactionManageEvent.TargetAmountChange) {
        update {
            it.copy(
                exchangeState = it.exchangeState.copy(
                    sourceAmount = event.amount * it.exchangeState.rate,
                    receiverAmount = event.amount
                )
            )
        }
    }

    private fun onCommentChange(event: TransactionManageEvent.CommentChange) {
        update {
            it.copy(comment = event.comment.takeIf { comment -> comment.isNotBlank() })
        }
    }

    private fun onDateSelect(event: TransactionManageEvent.DateSelect) {
        update {
            it.copy(date = event.date.atTime(LocalTime.now()))
        }
    }

    private fun onScheduledCheckChange(event: TransactionManageEvent.ScheduledCheckChange) {
        update { it.copy(isScheduled = event.isChecked) }
    }

    private fun onApply() {
        when (params.mode) {
            TransactionManageMode.ADD -> addTransaction()
            TransactionManageMode.EDIT -> updateTransaction()
        }
    }

    private fun updateTransaction() {
        with(state.value) {
            if (accounts.isNotEmpty() && params.transactionId != null) {
                val (sourceAccount, receiverAccount) = accounts
                val transaction = Transaction(
                    id = params.transactionId,
                    ledgers = listOf(
                        Ledger(0L, sourceAccount, LedgerType.CREDIT, exchangeState.sourceAmount),
                        Ledger(0L, receiverAccount, LedgerType.DEBIT, exchangeState.receiverAmount)
                    ),
                    dateTime = date,
                    comment = comment,
                    isScheduled = isScheduled
                )
                launch {
                    updateTransactionUseCase(transaction)
                    router.back()
                }
            }
        }
    }

    private fun addTransaction() {
        with(state.value) {
            if (accounts.isNotEmpty()) {
                val (sourceAccount, receiverAccount) = accounts
                val transaction = Transaction(
                    id = 0L,
                    ledgers = listOf(
                        Ledger(0L, sourceAccount, LedgerType.CREDIT, exchangeState.sourceAmount),
                        Ledger(0L, receiverAccount, LedgerType.DEBIT, exchangeState.receiverAmount)
                    ),
                    dateTime = date,
                    comment = comment,
                    isScheduled = isScheduled
                )
                launch {
                    addTransactionUseCase(transaction)
                    router.back()
                }
            }
        }
    }

    private fun getExchangeRate(
        rates: List<CurrencyRate>,
        sourceCurrency: Currency,
        targetCurrency: Currency
    ): Double {
        val sourceRate = rates.first { it.currency == sourceCurrency }
        val targetRate = rates.first { it.currency == targetCurrency }
        return sourceRate.rate / targetRate.rate
    }
}
