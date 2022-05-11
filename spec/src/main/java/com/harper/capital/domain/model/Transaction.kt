package com.harper.capital.domain.model

import java.time.LocalDateTime

open class Transaction(
    open val id: Long = 0L,
    val ledgers: List<Ledger>,
    open val dateTime: LocalDateTime,
    open val comment: String?,
    open val isScheduled: Boolean
)

data class TransferTransaction(
    override val id: Long = 0L,
    val source: Account,
    val receiver: Account,
    val sourceAmount: Double,
    val receiverAmount: Double,
    override val dateTime: LocalDateTime,
    override val comment: String?,
    override val isScheduled: Boolean
) : Transaction(
    id,
    ledgers = listOf(
        Ledger(
            account = source,
            type = if (source.type == AccountType.ASSET) LedgerType.CREDIT else LedgerType.DEBIT,
            amount = sourceAmount
        ),
        Ledger(
            account = receiver,
            type = if (receiver.type == AccountType.ASSET) LedgerType.DEBIT else LedgerType.CREDIT,
            amount = receiverAmount
        )
    ),
    dateTime = dateTime,
    comment = comment,
    isScheduled = isScheduled
)

data class ChangeTransaction(
    override val id: Long = 0L,
    val account: Account,
    val amount: Double,
    override val dateTime: LocalDateTime,
    override val comment: String?,
    override val isScheduled: Boolean
) : Transaction(
    id,
    ledgers = listOf(
        Ledger(account = account, type = if (account.type == AccountType.ASSET) LedgerType.DEBIT else LedgerType.CREDIT, amount = amount)
    ),
    dateTime = dateTime,
    comment = comment,
    isScheduled = isScheduled
)
