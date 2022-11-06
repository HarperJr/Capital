package com.harper.capital.transaction.model

import com.harper.capital.domain.model.AccountType

sealed class TransactionEvent {

    class TabSelect(val tabIndex: Int) : TransactionEvent()

    class NewAccountClick(val transactionType: TransactionType, val type: AccountType) : TransactionEvent()

    class SourceAccountSelect(val type: TransactionType, val accountId: Long) : TransactionEvent()

    class ReceiverAccountSelect(val type: TransactionType, val accountId: Long) : TransactionEvent()

    object BackClick : TransactionEvent()
}
