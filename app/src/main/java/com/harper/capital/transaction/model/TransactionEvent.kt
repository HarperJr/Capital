package com.harper.capital.transaction.model

sealed class TransactionEvent {

    class OnTabSelect(val tabIndex: Int) : TransactionEvent()

    object BackClick : TransactionEvent()
}
