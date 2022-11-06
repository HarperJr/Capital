package com.harper.capital.transaction.manage

import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.ext.getExchangeRate
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.domain.AddTransactionUseCase
import com.harper.capital.transaction.manage.domain.FetchAccountUseCase
import com.harper.capital.transaction.manage.domain.FetchCurrencyRatesUseCase
import com.harper.capital.transaction.manage.domain.FetchTransactionUseCase
import com.harper.capital.transaction.manage.domain.UpdateTransactionUseCase
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.ext.orElse
import com.harper.core.ui.ComponentViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import kotlinx.coroutines.launch

class TransactionManageViewModel(
    private val params: TransactionManageParams,
    private val router: GlobalRouter,
    private val fetchAccountUseCase: FetchAccountUseCase,
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
            is TransactionManageEvent.Init -> onInit(event)
        }
    }

    override fun onFirstComposition() {
        super.onFirstComposition()
        launch {
            if (params.transactionId == null) {
                fetchAccounts()
            } else {
                fetchTransaction(params.transactionId)
            }
            val rates = fetchCurrencyRatesUseCase()
            update { prevState ->
                prevState.transaction
                    ?.let {
                        prevState.copy(exchangeRate = it.source.currency.getExchangeRate(rates, it.receiver.currency))
                    }.orElse(prevState)
            }
        }
    }

    private suspend fun fetchTransaction(transactionId: Long) {
        val transaction = fetchTransactionUseCase(transactionId)
        update {
            if (transaction is TransferTransaction) {
                it.copy(
                    transaction = transaction,
                    isLoading = false
                )
            } else {
                it.copy(isLoading = false)
            }
        }
    }

    private suspend fun fetchAccounts() {
        val sourceId = params.sourceAccountId
        val receiverId = params.receiverAccountId
        if (sourceId != null && receiverId != null) {
            val source = fetchAccountUseCase(sourceId)
            val receiver = fetchAccountUseCase(receiverId)
            val transaction = TransferTransaction(
                source = source,
                receiver = receiver,
                sourceAmount = 0.0,
                receiverAmount = 0.0,
                dateTime = LocalDateTime.now(),
                comment = null,
                isScheduled = false
            )
            update {
                it.copy(transaction = transaction, isLoading = false)
            }
        }
    }

    private fun onSourceAmountChange(event: TransactionManageEvent.SourceAmountChange) {
        update {
            it.copy(
                transaction = it.transaction?.copy(
                    sourceAmount = event.amount,
                    receiverAmount = event.amount / it.exchangeRate,
                )
            )
        }
    }

    private fun onInit(event: TransactionManageEvent.Init) {
        launch {
            val transaction = TransferTransaction(
                source = fetchAccountUseCase(event.sourceId),
                receiver = fetchAccountUseCase(event.receiverId),
                sourceAmount = 0.0,
                receiverAmount = 0.0,
                dateTime = LocalDateTime.now(),
                comment = null,
                isScheduled = false
            )
            update { it.copy(transaction = transaction, isLoading = false) }
        }
    }

    private fun onTargetAmountChange(event: TransactionManageEvent.TargetAmountChange) {
        update {
            it.copy(
                transaction = it.transaction?.copy(
                    sourceAmount = event.amount * it.exchangeRate,
                    receiverAmount = event.amount
                )
            )
        }
    }

    private fun onCommentChange(event: TransactionManageEvent.CommentChange) {
        update {
            it.copy(transaction = it.transaction?.copy(comment = event.comment.takeIf { comment -> comment.isNotBlank() }))
        }
    }

    private fun onDateSelect(event: TransactionManageEvent.DateSelect) {
        update {
            val time = it.transaction?.dateTime?.toLocalTime().orElse(LocalTime.now())
            it.copy(transaction = it.transaction?.copy(dateTime = event.date.atTime(time)))
        }
    }

    private fun onScheduledCheckChange(event: TransactionManageEvent.ScheduledCheckChange) {
        update { it.copy(transaction = it.transaction?.copy(isScheduled = event.isChecked)) }
    }

    private fun onApply() {
        when (params.mode) {
            TransactionManageMode.ADD -> addTransaction()
            TransactionManageMode.EDIT -> updateTransaction()
        }
    }

    private fun updateTransaction() {
        state.value.transaction?.let {
            launch {
                updateTransactionUseCase(it)
                router.back()
            }
        }
    }

    private fun addTransaction() {
        state.value.transaction?.let {
            launch {
                addTransactionUseCase(it)
                router.back()
            }
        }
    }
}
