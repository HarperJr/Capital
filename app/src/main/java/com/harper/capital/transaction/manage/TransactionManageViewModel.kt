package com.harper.capital.transaction.manage

import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.domain.AddTransactionUseCase
import com.harper.capital.transaction.manage.domain.FetchAssetUseCase
import com.harper.capital.transaction.manage.domain.FetchTransactionUseCase
import com.harper.capital.transaction.manage.domain.UpdateTransactionUseCase
import com.harper.capital.transaction.manage.model.AssetPair
import com.harper.capital.transaction.manage.model.DatePickerDialogState
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.ext.cast
import com.harper.core.ext.orElse
import com.harper.core.ui.ComponentViewModel
import java.time.LocalTime
import kotlin.math.abs

class TransactionManageViewModel(
    private val params: TransactionManageParams,
    private val router: GlobalRouter,
    private val fetchAssetUseCase: FetchAssetUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val fetchTransactionUseCase: FetchTransactionUseCase
) : ComponentViewModel<TransactionManageState, TransactionManageEvent>(
    initialState = TransactionManageState(mode = params.mode)
) {

    override fun onEvent(event: TransactionManageEvent) {
        when (event) {
            TransactionManageEvent.BackClick -> router.back()
            is TransactionManageEvent.AmountChange -> onAmountChange(event)
            is TransactionManageEvent.CommentChange -> onCommentChange(event)
            is TransactionManageEvent.DateSelect -> onDateSelect(event)
            is TransactionManageEvent.DateSelectClick -> onDateSelectClick(event)
            is TransactionManageEvent.ScheduledCheckChange -> onScheduledCheckChange(event)
            TransactionManageEvent.HideDialog -> onHideDialog()
            TransactionManageEvent.Apply -> onApply()
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
            val transaction = fetchTransactionUseCase(transactionId).cast<TransferTransaction>()
            update {
                it.copy(
                    accountPair = AssetPair(transaction.source, transaction.receiver),
                    amount = abs(transaction.amount),
                    currency = transaction.source.currency,
                    date = transaction.dateTime.toLocalDate(),
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
            update {
                it.copy(
                    accountPair = AssetPair(sourceAccount, receiverAccount),
                    currency = sourceAccount.currency,
                    isLoading = false
                )
            }
        }
    }

    private fun onHideDialog() {
        update {
            it.copy(datePickerDialogState = DatePickerDialogState(isVisible = false))
        }
    }

    private fun onAmountChange(event: TransactionManageEvent.AmountChange) {
        update {
            it.copy(amount = event.amount)
        }
    }

    private fun onCommentChange(event: TransactionManageEvent.CommentChange) {
        update {
            it.copy(comment = event.comment.takeIf { comment -> comment.isNotEmpty() })
        }
    }

    private fun onDateSelect(event: TransactionManageEvent.DateSelect) {
        update {
            it.copy(date = event.date)
        }
    }

    private fun onDateSelectClick(event: TransactionManageEvent.DateSelectClick) {
        update {
            it.copy(datePickerDialogState = DatePickerDialogState(event.date, isVisible = true))
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
            if (accountPair != null) {
                val (sourceAccount, receiverAccount) = accountPair
                val transaction = TransferTransaction(
                    id = params.transactionId.orElse(0L),
                    source = sourceAccount,
                    receiver = receiverAccount,
                    amount = amount,
                    dateTime = date.atTime(LocalTime.now()),
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
            if (accountPair != null) {
                val (sourceAccount, receiverAccount) = accountPair
                val transaction = TransferTransaction(
                    id = 0L,
                    source = sourceAccount,
                    receiver = receiverAccount,
                    amount = amount,
                    dateTime = date.atTime(LocalTime.now()),
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
}
