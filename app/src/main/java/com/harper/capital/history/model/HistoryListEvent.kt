package com.harper.capital.history.model

sealed class HistoryListEvent {

    class OnTransactionClick(val transactionId: Long) : HistoryListEvent()

    object BackClick : HistoryListEvent()

    object FilterItemClick : HistoryListEvent()
}
