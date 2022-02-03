package com.harper.capital.transaction.manage

import com.harper.capital.domain.model.Transaction
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.transaction.manage.domain.AddTransactionUseCase
import com.harper.capital.transaction.manage.domain.FetchAssetUseCase
import com.harper.capital.transaction.manage.model.AssetPair
import com.harper.capital.transaction.manage.model.DatePickerDialogState
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import java.time.LocalTime

class TransactionManageViewModel(
    private val params: TransactionManageFragment.Params,
    private val router: GlobalRouter,
    private val fetchAssetUseCase: FetchAssetUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ComponentViewModel<TransactionManageState>(
    defaultState = TransactionManageState(transactionType = params.transactionType)
), EventObserver<TransactionManageEvent> {

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

    override fun onFirstStart() {
        super.onFirstStart()
        launch {
            val assetFrom = fetchAssetUseCase(params.assetFromId)
            val assetTo = fetchAssetUseCase(params.assetToId)
            mutateState {
                it.copy(
                    assetPair = AssetPair(assetFrom, assetTo),
                    currency = assetFrom.currency,
                    isLoading = false
                )
            }
        }
    }

    private fun onHideDialog() {
        mutateState {
            it.copy(datePickerDialogState = DatePickerDialogState(isVisible = false))
        }
    }

    private fun onAmountChange(event: TransactionManageEvent.AmountChange) {
        mutateState {
            it.copy(amount = event.amount)
        }
    }

    private fun onCommentChange(event: TransactionManageEvent.CommentChange) {
        mutateState {
            it.copy(comment = event.comment.takeIf { comment -> comment.isNotEmpty() })
        }
    }

    private fun onDateSelect(event: TransactionManageEvent.DateSelect) {
        mutateState {
            it.copy(date = event.date)
        }
    }

    private fun onDateSelectClick(event: TransactionManageEvent.DateSelectClick) {
        mutateState {
            it.copy(datePickerDialogState = DatePickerDialogState(event.date, isVisible = true))
        }
    }

    private fun onScheduledCheckChange(event: TransactionManageEvent.ScheduledCheckChange) {
        mutateState { it.copy(isScheduled = event.isChecked) }
    }

    private fun onApply() {
        with(state.value) {
            if (assetPair != null) {
                val (assetFrom, assetTo) = assetPair
                val transaction = Transaction(
                    id = 0L,
                    source = assetFrom,
                    receiver = assetTo,
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
